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
package fr.paris.lutece.plugins.participatorybudget.web.campaign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.participatorybudget.business.campaign.Campagne;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneHome;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagnePhase;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagnePhaseHome;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneTheme;
import fr.paris.lutece.plugins.participatorybudget.business.campaign.CampagneThemeHome;
import fr.paris.lutece.plugins.participatorybudget.service.campaign.CampaignService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage Ideation Campagnes feature : generating new campagne
 */
@Controller( controllerJsp = "ManageCampaignChrono.jsp", controllerPath = "jsp/admin/plugins/participatorybudget/campaign/", right = "CAMPAGNEBP_MANAGEMENT" )
public class CampaignChronoJspBean extends ManageCampagnebpJspBean
{

    // //////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_IDEATIONCAMPAGNES = "/admin/plugins/participatorybudget/campaign/manage_campaignchrono.html";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_IDEATIONCAMPAGNES = "participatorybudget.manage_campaignchrono.pageTitle";

    // Markers
    private static final String MARK_IDEATIONCAMPAGNE_LIST = "ideationcampagne_list";
    private static final String MARK_PHASE_MAP = "phase_map";
    private static final String MARK_THEME_MAP = "theme_map";

    private static final String JSP_MANAGE_CAMPAIGNCHRONO = "jsp/admin/plugins/participatorybudget/campaign/ManageCampaignChrono.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_CLONE_CAMPAIGNCHRONO = "participatorybudget.manage_campaignchrono.confirmCloneCampagneChrono";

    // Request Parameter
    private static final String PARAMETER_CAMPAIGN_ID = "campaign_id";

    // Views
    private static final String VIEW_MANAGE_CAMPAIGNCHRONO = "manageCampaignChrono";
    private static final String VIEW_CONFIRM_CLONE_CAMPAIGNCHRONO = "confirmGenerateCampaignChrono";

    // Actions
    private static final String ACTION_CLONE_CAMPAIGNCHRONO = "cloneCampaignChrono";

    // Infos
    private static final String INFO_CAMPAIGNCHRONO_CLONED = "participatorybudget.manage_campaignchrono.cloned";

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_CAMPAIGNCHRONO, defaultView = true )
    public String getManageIdeationCampagnes( HttpServletRequest request )
    {
        // Adding campagnes in model
        List<Campagne> campagnes = new ArrayList<Campagne>( CampagneHome.getCampagnesList( ) );
        Map<String, Object> model = getPaginatedListModel( request, MARK_IDEATIONCAMPAGNE_LIST, campagnes, JSP_MANAGE_CAMPAIGNCHRONO );

        // Adding phases in model, sorted by start phase in ascending order
        Map<String, List<CampagnePhase>> phaseMap = new HashMap<String, List<CampagnePhase>>( );
        List<CampagnePhase> phases = new ArrayList<CampagnePhase>( CampagnePhaseHome.getCampagnePhasesList( ) );
        for ( CampagnePhase phase : phases )
        {
            if ( phaseMap.get( phase.getCodeCampagne( ) ) == null )
                phaseMap.put( phase.getCodeCampagne( ), new ArrayList<CampagnePhase>( ) );
            phaseMap.get( phase.getCodeCampagne( ) ).add( phase );
        }
        phaseMap.forEach( ( k, v ) -> Collections.sort( v, new Comparator<CampagnePhase>( )
        {
            @Override
            public int compare( CampagnePhase phase1, CampagnePhase phase2 )
            {
                return ( phase1.getStart( ).before( phase2.getStart( ) ) ? -1 : 1 );
            }
        } ) );
        model.put( MARK_PHASE_MAP, phaseMap );

        // Adding themes in model, sorted by code in ascending order
        Map<String, List<CampagneTheme>> themeMap = new HashMap<String, List<CampagneTheme>>( );
        List<CampagneTheme> themes = new ArrayList<CampagneTheme>( CampagneThemeHome.getCampagneThemesList( ) );
        for ( CampagneTheme theme : themes )
        {
            if ( themeMap.get( theme.getCodeCampagne( ) ) == null )
                themeMap.put( theme.getCodeCampagne( ), new ArrayList<CampagneTheme>( ) );
            themeMap.get( theme.getCodeCampagne( ) ).add( theme );
        }
        themeMap.forEach( ( k, v ) -> Collections.sort( v, new Comparator<CampagneTheme>( )
        {
            @Override
            public int compare( CampagneTheme theme1, CampagneTheme theme2 )
            {
                return ( theme1.getCode( ).compareTo( theme2.getCode( ) ) );
            }
        } ) );
        model.put( MARK_THEME_MAP, themeMap );

        // Adding depositaires in model, sorted by code in ascending order
        // Map<String, List<CampagneDepositaire>> depositaireMap = new HashMap<String, List<CampagneDepositaire>>();
        // List<CampagneDepositaire> depositaires = new ArrayList<CampagneDepositaire>( CampagneDepositaireHome.getCampagneDepositairesList() );
        // for (CampagneDepositaire depositaire : depositaires) {
        // if ( depositaireMap.get(depositaire.getCodeCampagne()) == null )
        // depositaireMap.put(depositaire.getCodeCampagne(), new ArrayList<CampagneDepositaire>());
        // depositaireMap.get(depositaire.getCodeCampagne()).add(depositaire);
        // }
        // depositaireMap.forEach((k,v) ->
        // Collections.sort(v, new Comparator<CampagneDepositaire>() {
        // @Override
        // public int compare(CampagneDepositaire depositaire1, CampagneDepositaire depositaire2)
        // {
        // return ( depositaire1.getCodeDepositaireType().compareTo(depositaire2.getCodeDepositaireType() ));
        // }
        // })
        // );
        // model.put( MARK_DEPOSITAIRE_MAP, depositaireMap);

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_IDEATIONCAMPAGNES, TEMPLATE_MANAGE_IDEATIONCAMPAGNES, model );
    }

    // ***********************************************************************************
    // * GENERATE GENERATE GENERATE GENERATE GENERATE GENERATE GENERATE GENERATE GENERAT *
    // * GENERATE GENERATE GENERATE GENERATE GENERATE GENERATE GENERATE GENERATE GENERAT *
    // ***********************************************************************************

    /**
     * Confirmation of cloning a campaign request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @View( VIEW_CONFIRM_CLONE_CAMPAIGNCHRONO )
    public String getConfirmCloneCampaign( HttpServletRequest request )
    {
        UrlItem url = new UrlItem( getActionUrl( ACTION_CLONE_CAMPAIGNCHRONO ) );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_CLONE_CAMPAIGNCHRONO, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the clone process of a campaign
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display after cloning
     */
    @Action( ACTION_CLONE_CAMPAIGNCHRONO )
    public String doCloneCampaign( HttpServletRequest request )
    {
        int campaignId = Integer.parseInt( request.getParameter( PARAMETER_CAMPAIGN_ID ) );

        int newCampaignId = CampaignService.getInstance( ).clone( campaignId );

        String msg = I18nService.getLocalizedString( INFO_CAMPAIGNCHRONO_CLONED, new String [ ] {
                campaignId + "", newCampaignId + ""
        }, getLocale( ) );
        addInfo( msg );

        return redirectView( request, VIEW_MANAGE_CAMPAIGNCHRONO );
    }

}
