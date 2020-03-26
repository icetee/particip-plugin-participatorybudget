/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.participatorybudget.service.campaign;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.participatorybudget.business.campaign.Campagne;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneArea;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneAreaHome;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneHome;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagnePhase;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagnePhaseHome;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneTheme;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneThemeHome;
import fr.paris.lutece.plugins.participatorybudget.service.NoSuchPhaseException;
import fr.paris.lutece.plugins.participatorybudget.service.campaign.event.CampaignEvent;
import fr.paris.lutece.plugins.participatorybudget.service.campaign.event.CampaignEventListener;
import fr.paris.lutece.plugins.participatorybudget.service.campaign.event.CampaignEventListernersManager;
import fr.paris.lutece.plugins.participatorybudget.util.Constants;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;

public class CampaignService implements ICampaignService
{

    private static final String BEAN_CAMPAGNE_SERVICE = "participatorybudget.campaignService";
    private static final String BEAN_EVENT_LISTENER_MANAGER = "participatorybudget.campaignEventListernersManager";

    // Attributes
    private Map<String, Timestamp> _cache = null;

    private static ICampaignService _singleton;
    private static CampaignEventListernersManager _managerEventListenersManager = null;

    // ***********************************************************************************
    // * SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON *
    // * SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON SINGLETON *
    // ***********************************************************************************

    public static ICampaignService getInstance( )
    {
        if ( _singleton == null )
        {
            _singleton = SpringContextService.getBean( BEAN_CAMPAGNE_SERVICE );

            _managerEventListenersManager = SpringContextService.getBean( BEAN_EVENT_LISTENER_MANAGER );
            _managerEventListenersManager.setListeners( SpringContextService.getBeansOfType( CampaignEventListener.class ) );
        }
        return _singleton;
    }

    // ***********************************************************************************
    // * CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE C *
    // * CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE CACHE C *
    // ***********************************************************************************

    private Map<String, Timestamp> getCache( )
    {
        if ( _cache == null )
            reset( );

        return _cache;
    }

    private String getKey( String campain, String phase, String datetimeType )
    {
        return campain + "-" + phase + "-" + datetimeType;
    }

    public void reset( )
    {
        AppLogService.debug( "CampagnePhase cache reset" );

        Map<String, Timestamp> cache = new HashMap<String, Timestamp>( );

        Collection<CampagnePhase> phases = CampagnePhaseHome.getCampagnePhasesList( );
        for ( CampagnePhase phase : phases )
        {
            String beginningKey = getKey( phase.getCodeCampagne( ), phase.getCodePhaseType( ), Constants.BEGINNING_DATETIME );
            String endKey = getKey( phase.getCodeCampagne( ), phase.getCodePhaseType( ), Constants.END_DATETIME );

            cache.put( beginningKey, phase.getStart( ) );
            cache.put( endKey, phase.getEnd( ) );

            AppLogService.debug(
                    "  -> Added '" + phase.getCodeCampagne( ) + "-" + phase.getCodePhaseType( ) + "' = '" + phase.getStart( ) + "/" + phase.getEnd( ) + "'." );
        }

        _cache = cache;
    }

    // ***********************************************************************************
    // * CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN *
    // * CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN CAMPAIN *
    // ***********************************************************************************

    public Campagne getLastCampagne( )
    {
        return CampagneHome.getLastCampagne( );
    }

    // ***********************************************************************************
    // * PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE P *
    // * PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE PHASE P *
    // ***********************************************************************************

    private Timestamp getTimestamp( String campain, String phase, String timestampType )
    {
        String key = getKey( campain, phase, timestampType );
        Timestamp timeStamp = getCache( ).get( key );
        if ( timeStamp == null )
        {
            String errorMsg = "Null datetime for campagne '" + campain + "' and phase '" + phase + "' and timestampType '" + timestampType + "'. ";
            AppLogService.error( errorMsg );
            throw new NoSuchPhaseException( errorMsg );
        }
        return timeStamp;
    }

    public boolean isBeforeBeginning( String campain, String phase )
    {
        Timestamp timeStamp = getTimestamp( campain, phase, Constants.BEGINNING_DATETIME );
        Date date = new Date( );
        boolean result = date.before( timeStamp );
        return result;
    }

    public boolean isBeforeEnd( String campain, String phase )
    {
        Timestamp timeStamp = getTimestamp( campain, phase, Constants.END_DATETIME );
        Date date = new Date( );
        boolean result = date.before( timeStamp );
        return result;
    }

    public boolean isDuring( String campain, String phase )
    {
        Timestamp beginningTimeStamp = getTimestamp( campain, phase, Constants.BEGINNING_DATETIME );
        Timestamp endTimeStamp = getTimestamp( campain, phase, Constants.END_DATETIME );

        Date date = new Date( );

        boolean result = date.after( beginningTimeStamp ) && date.before( endTimeStamp );
        return result;
    }

    public boolean isAfterBeginning( String campain, String phase )
    {
        Timestamp timeStamp = getTimestamp( campain, phase, Constants.BEGINNING_DATETIME );
        Date date = new Date( );
        boolean result = date.after( timeStamp );
        return result;
    }

    public boolean isAfterEnd( String campain, String phase )
    {
        Timestamp timeStamp = getTimestamp( campain, phase, Constants.END_DATETIME );
        Date date = new Date( );
        boolean result = date.after( timeStamp );
        return result;
    }

    public Timestamp start( String campain, String phase )
    {
        Timestamp timeStamp = getTimestamp( campain, phase, Constants.BEGINNING_DATETIME );
        return timeStamp;
    }

    public Timestamp end( String campain, String phase )
    {
        Timestamp timeStamp = getTimestamp( campain, phase, Constants.END_DATETIME );
        return timeStamp;
    }

    public String startStr( String campain, String phase, String format, boolean withAccents )
    {
        Timestamp ts = start( campain, phase );
        Date date = new Date( );
        date.setTime( ts.getTime( ) );
        String formattedDate = new SimpleDateFormat( format ).format( date );
        return toSoLovelyString( formattedDate, withAccents );
    }

    public String endStr( String campain, String phase, String format, boolean withAccents )
    {
        Timestamp ts = end( campain, phase );
        Date date = new Date( );
        date.setTime( ts.getTime( ) );
        String formattedDate = new SimpleDateFormat( format ).format( date );
        return toSoLovelyString( formattedDate, withAccents );
    }

    // Same methods than preceding, for last campagne

    public boolean isBeforeBeginning( String phase )
    {
        return isBeforeBeginning( getLastCampagne( ).getCode( ), phase );
    }

    public boolean isBeforeEnd( String phase )
    {
        return isBeforeEnd( getLastCampagne( ).getCode( ), phase );
    }

    public boolean isDuring( String phase )
    {
        return isDuring( getLastCampagne( ).getCode( ), phase );
    }

    public boolean isAfterBeginning( String phase )
    {
        return isAfterBeginning( getLastCampagne( ).getCode( ), phase );
    }

    public boolean isAfterEnd( String phase )
    {
        return isAfterEnd( getLastCampagne( ).getCode( ), phase );
    }

    public Timestamp start( String phase )
    {
        return start( getLastCampagne( ).getCode( ), phase );
    }

    public Timestamp end( String phase )
    {
        return end( getLastCampagne( ).getCode( ), phase );
    }

    public String startStr( String phase, String format, boolean withAccents )
    {
        return startStr( getLastCampagne( ).getCode( ), phase, format, withAccents );
    }

    public String endStr( String phase, String format, boolean withAccents )
    {
        return endStr( getLastCampagne( ).getCode( ), phase, format, withAccents );
    }

    // ***********************************************************************************
    // * AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS A *
    // * AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS AREAS A *
    // ***********************************************************************************

    public List<String> getAllAreas( String codeCampaign )
    {
        Collection<CampagneArea> result = CampagneAreaHome.getCampagneAreasListByCampagne( codeCampaign );
        List<String> areas = new ArrayList<>( );
        for ( CampagneArea c : result )
        {
            if ( c.getActive( ) )
            {
                areas.add( c.getTitle( ) );
            }
        }
        return areas;
    }

    public List<String> getLocalizedAreas( String codeCampaign )
    {
        Collection<CampagneArea> result = CampagneAreaHome.getCampagneAreasListByCampagne( codeCampaign );
        List<String> areas = new ArrayList<>( );
        for ( CampagneArea c : result )
        {
            if ( c.getActive( ) && c.getType( ).equals( "localized" ) )
            {
                areas.add( c.getTitle( ) );
            }
        }
        return areas;
    }

    public boolean hasWholeArea( String codeCampaign )
    {
        Collection<CampagneArea> areas = CampagneAreaHome.getCampagneAreasListByCampagne( codeCampaign );
        for ( CampagneArea a : areas )
        {
            if ( a.getType( ).equals( "whole" ) )
            {
                return true;
            }
        }
        return false;
    }

    public boolean hasWholeArea( String codeCampaign, int idCampaign )
    {
        Collection<CampagneArea> areas = CampagneAreaHome.getCampagneAreasListByCampagne( codeCampaign );
        for ( CampagneArea a : areas )
        {
            if ( a.getType( ).equals( "whole" ) && a.getId( ) != idCampaign )
            {
                return true;
            }
        }
        return false;
    }

    public String getWholeArea( String codeCampaign )
    {
        Collection<CampagneArea> areas = CampagneAreaHome.getCampagneAreasListByCampagne( codeCampaign );
        for ( CampagneArea a : areas )
        {
            if ( a.getType( ).equals( "whole" ) && a.getActive( ) )
            {
                return a.getTitle( );
            }
        }
        return "";
    }

    // Same methods than preceding, for last campagne

    public List<String> getAllAreas( )
    {
        return getAllAreas( getLastCampagne( ).getCode( ) );
    }

    public List<String> getLocalizedAreas( )
    {
        return getLocalizedAreas( getLastCampagne( ).getCode( ) );
    }

    public boolean hasWholeArea( )
    {
        return hasWholeArea( getLastCampagne( ).getCode( ) );
    }

    public boolean hasWholeArea( int id )
    {
        return hasWholeArea( getLastCampagne( ).getCode( ), id );
    }

    public String getWholeArea( )
    {
        return getWholeArea( getLastCampagne( ).getCode( ) );
    }

    // ***********************************************************************************
    // * THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES TH *
    // * THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES TH *
    // ***********************************************************************************

    @Override
    public ReferenceList getThemes( String codeCampaign )
    {
        ReferenceList items = new ReferenceList( );

        Collection<CampagneTheme> list = CampagneThemeHome.getCampagneThemesListByCampagne( codeCampaign );
        for ( CampagneTheme theme : list )
        {
            items.addItem( theme.getCode( ), theme.getTitle( ) );
        }

        return items;
    }

    @Override
    public ReferenceList getThemes( )
    {
        return getThemes( getLastCampagne( ).getCode( ) );
    }

    // *********************************************************************************************
    // * CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE *
    // * CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE CLONE *
    // *********************************************************************************************

    /**
     * {@inheritDoc}
     */
    @Override
    public int clone( int campaignId )
    {
        // Creates new campagne ---------------------------------------------------------------------------

        Campagne campaignToClone = CampagneHome.findByPrimaryKey( campaignId );

        // Generate new code, max 50 chars
        String newCampagneCode = StringUtils.abbreviate( "(clone) " + campaignToClone.getCode( ), 50 );

        Campagne newCampagne = new Campagne( );
        newCampagne.setCode( newCampagneCode + "" );
        newCampagne.setTitle( "(clone) " + campaignToClone.getTitle( ) );
        newCampagne.setDescription( "(clone) " + campaignToClone.getDescription( ) );
        newCampagne.setActive( false );
        newCampagne.setCodeModerationType( campaignToClone.getCodeModerationType( ) );
        newCampagne.setModerationDuration( campaignToClone.getModerationDuration( ) );

        CampagneHome.create( newCampagne );

        // Creates phases ---------------------------------------------------------------------------------

        Collection<CampagnePhase> lastPhases = CampagnePhaseHome.getCampagnePhasesListByCampagne( campaignToClone.getCode( ) );

        for ( CampagnePhase lastPhase : lastPhases )
        {

            CampagnePhase phase = new CampagnePhase( );

            Calendar newStart = Calendar.getInstance( );
            newStart.setTime( lastPhase.getStart( ) );
            newStart.add( Calendar.YEAR, 1 );
            Calendar newEnd = Calendar.getInstance( );
            newEnd.setTime( lastPhase.getEnd( ) );
            newEnd.add( Calendar.YEAR, 1 );

            phase.setCodePhaseType( lastPhase.getCodePhaseType( ) );
            phase.setCodeCampagne( "" + newCampagneCode );
            phase.setStart( new Timestamp( newStart.getTimeInMillis( ) ) );
            phase.setEnd( new Timestamp( newEnd.getTimeInMillis( ) ) );

            CampagnePhaseHome.create( phase );
        }

        // Creates themes ---------------------------------------------------------------------------------

        Collection<CampagneTheme> lastThemes = CampagneThemeHome.getCampagneThemesListByCampagne( campaignToClone.getCode( ) );

        for ( CampagneTheme lastTheme : lastThemes )
        {

            CampagneTheme theme = new CampagneTheme( );

            theme.setCode( lastTheme.getCode( ) );
            theme.setCodeCampagne( "" + newCampagneCode );
            theme.setTitle( lastTheme.getTitle( ) );
            theme.setDescription( lastTheme.getDescription( ) );
            theme.setActive( true );

            CampagneThemeHome.create( theme );
        }

        // Creates depositary -----------------------------------------------------------------------------

        // Collection<CampagneDepositaire> lastDepositaires = CampagneDepositaireHome.getCampagneDepositaireListByCampagne( lastCampagne.getCode() );
        //
        // for (CampagneDepositaire lastDepositaire : lastDepositaires) {
        //
        // CampagneDepositaire depositaire = new CampagneDepositaire();
        //
        // depositaire.setCodeDepositaireType ( lastDepositaire.getCodeDepositaireType() );
        // depositaire.setCodeCampagne ( "" + newCampagneCode );
        //
        // CampagneDepositaireHome.create( depositaire );
        // }

        // Reseting cache

        CampaignService.getInstance( ).reset( );

        _managerEventListenersManager.notifyListeners( new CampaignEvent( newCampagne, campaignToClone, CampaignEvent.CAMPAIGN_CLONED ) );

        return newCampagne.getId( );
    }

    // ***********************************************************************************
    // * UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS U *
    // * UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS UTILS U *
    // ***********************************************************************************

    private String toSoLovelyString( String msg, boolean withAccents )
    {
        String soLovelyStr = null;

        // Deleting accents
        if ( !withAccents )
        {
            soLovelyStr = Normalizer.normalize( msg, Normalizer.Form.NFD ).replaceAll( "\\p{InCombiningDiacriticalMarks}+", "" );
        }
        else
        {
            soLovelyStr = msg;
        }

        // Replacing "01" by "1er"
        if ( soLovelyStr.startsWith( "01" ) )
        {
            soLovelyStr = "1er " + soLovelyStr.substring( 3 );
        }

        // Deleting "0" if first char
        if ( soLovelyStr.charAt( 0 ) == '0' )
        {
            soLovelyStr = soLovelyStr.substring( 1 );
        }

        return soLovelyStr;
    }

}
