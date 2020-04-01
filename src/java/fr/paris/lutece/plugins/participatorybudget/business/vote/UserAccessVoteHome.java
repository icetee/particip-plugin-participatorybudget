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
package fr.paris.lutece.plugins.participatorybudget.business.vote;

import fr.paris.lutece.plugins.participatorybudget.util.Constants;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * This class provides instances management methods (create, find, ...) for Vote objects
 */
public class UserAccessVoteHome

{

    // Static variable pointed at the DAO instance
    private static IUserAccessVoteDAO _dao = (IUserAccessVoteDAO) SpringContextService.getBean( "participatorybudget.userAccessVoteDAO" );
    private static Plugin _plugin = PluginService.getPlugin( Constants.PLUGIN_NAME );

    /**
     * Private constructor - this class need not be instantiated
     */
    private UserAccessVoteHome( )
    {
    }

    /**
     * Insert User access vote
     * 
     * @param userAccess
     */
    public static void insertUserAccessVote( UserAccessVote userAccess )
    {
        _dao.insert( userAccess, _plugin );
    }

    /**
     * Update user accesss vote
     * 
     * @param userAccess
     *            the UserAccessVote
     */
    public static void updateUserAccessVote( UserAccessVote userAccess )
    {
        _dao.update( userAccess, _plugin );
    }

    /**
     * Delete a user access
     * 
     * @param strIdUser
     *            the Id user
     */
    public static void deleteUserAccessVote( String strIdUser )
    {
        _dao.delete( strIdUser, _plugin );
    }

    /**
     * Select a User access vote
     * 
     * @param strIdUser
     *            the Id user access
     */
    public static UserAccessVote selectUserAccessVote( String strIdUser )
    {
        return _dao.select( strIdUser, _plugin );
    }

}
