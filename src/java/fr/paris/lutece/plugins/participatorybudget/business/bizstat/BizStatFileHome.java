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
package fr.paris.lutece.plugins.participatorybudget.business.bizstat;

import java.util.List;

import fr.paris.lutece.plugins.participatorybudget.util.ParticipatoryBudgetConstants;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * This class provides instances management methods (create, find, ...) for physical file objects
 */
public final class BizStatFileHome
{
    // Static variable pointed at the DAO instance
    private static IBizStatFileDAO _dao = SpringContextService.getBean( "participatorybudget.bizStatFileDAO" );
    private static Plugin _plugin = PluginService.getPlugin( ParticipatoryBudgetConstants.PLUGIN_NAME );

    /**
     * Private constructor - this class need not be instantiated
     */
    private BizStatFileHome( )
    {
    }

    /**
     * Creation of a record
     */
    public static void create( BizStatFile file )
    {
        _dao.insert( file, _plugin );
    }

    /**
     * Update a record
     */
    public static void update( BizStatFile file )
    {
        _dao.update( file, _plugin );
    }

    /**
     * Delete a record
     */
    public static void remove( int nId )
    {
        _dao.delete( nId, _plugin );
    }

    /**
     * Returns a record, with binary content
     */
    public static BizStatFile findByPrimaryKey( int nKey )
    {
        return _dao.loadWithBytes( nKey, _plugin );
    }

    /**
     * Returns all records, WITHOUT binary content
     */
    public static List<BizStatFile> findAllWithoutBinaryContent( )
    {
        return _dao.selectAllWithoutBytes( _plugin );
    }

    /**
     * Returns records depending of the status, WITHOUT binary content
     */
    public static List<BizStatFile> findByStatusWithoutBinaryContent( String status )
    {
        return _dao.selectByStatusWithoutBytes( status, _plugin );
    }
}
