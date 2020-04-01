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
package fr.paris.lutece.plugins.participatorybudget.business;

import fr.paris.lutece.plugins.participatorybudget.business.vote.Vote;
import fr.paris.lutece.plugins.participatorybudget.business.vote.VoteHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;

import java.sql.Timestamp;

public class VoteBusinessTest extends LuteceTestCase
{
    private final static String IDUSER1 = "1";
    private final static int IDUSER2 = 2;
    private final static int IDPROJET1 = 1;
    private final static int IDPROJET2 = 2;
    private final static Timestamp DATEVOTE1 = new Timestamp( 2001L );
    private final static Timestamp DATEVOTE2 = new Timestamp( 2000L );
    private final static int ARRONDISSEMENT1 = 1;
    private final static int ARRONDISSEMENT2 = 2;
    private final static int AGE1 = 1;
    private final static int AGE2 = 2;
    private final static String IPADDRESS = "127.0.0.1";
    private final static String TITLE = "Title";
    private final static String LOCATION = "0";
    private final static String THEME = "Theme";

    public void testBusiness( )
    {
        // Initialize an object
        Vote vote = new Vote( );
        vote.setUserId( IDUSER1 );
        vote.setProjetId( IDPROJET1 );
        vote.setDateVote( DATEVOTE1 );
        vote.setArrondissement( ARRONDISSEMENT1 );
        vote.setAge( AGE1 );
        vote.setIpAddress( IPADDRESS );
        vote.setTitle( TITLE );
        vote.setLocalisation( LOCATION );
        vote.setThematique( THEME );

        // Create test
        VoteHome.create( vote );

        // List test
        VoteHome.getVotesList( );

        // Delete test
        VoteHome.remove( vote.getUserId( ), vote.getProjetId( ) );
    }
}
