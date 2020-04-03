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
package fr.paris.lutece.plugins.participatorybudget.business.campaign;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

import fr.paris.lutece.portal.business.file.File;

import java.io.Serializable;

/**
 * This is the business class for the object CampaignTheme
 */
public class CampaignTheme implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{participatorybudget.validation.campaigntheme.CodeCampaign.notEmpty}" )
    @Size( max = 50, message = "#i18n{participatorybudget.validation.campaigntheme.CodeCampaign.size}" )
    private String _strCodeCampaign;

    @NotEmpty( message = "#i18n{participatorybudget.validation.campaigntheme.Code.notEmpty}" )
    @Size( max = 50, message = "#i18n{participatorybudget.validation.campaigntheme.Code.size}" )
    private String _strCode;

    @NotEmpty( message = "#i18n{participatorybudget.validation.campaigntheme.Title.notEmpty}" )
    @Size( max = 50, message = "#i18n{participatorybudget.validation.campaigntheme.Title.size}" )
    private String _strTitle;

    @NotEmpty( message = "#i18n{participatorybudget.validation.campaigntheme.Description.notEmpty}" )
    private String _strDescription;

    private boolean _bActive;

    private File _fileImage;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the CodeCampaign
     * 
     * @return The CodeCampaign
     */
    public String getCodeCampaign( )
    {
        return _strCodeCampaign;
    }

    /**
     * Sets the CodeCampaign
     * 
     * @param strCodeCampaign
     *            The CodeCampaign
     */
    public void setCodeCampaign( String strCodeCampaign )
    {
        _strCodeCampaign = strCodeCampaign;
    }

    /**
     * Returns the Title
     * 
     * @return The Title
     */
    public String getTitle( )
    {
        return _strTitle;
    }

    /**
     * Sets the Title
     * 
     * @param strTitle
     *            The Title
     */
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }

    /**
     * Returns the Description
     * 
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * 
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the Active
     * 
     * @return The Active
     */
    public boolean getActive( )
    {
        return _bActive;
    }

    /**
     * Sets the Active
     * 
     * @param bActive
     *            The Active
     */
    public void setActive( boolean bActive )
    {
        _bActive = bActive;
    }

    /**
     * @return the Code
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * @param Code
     *            the Code to set
     */
    public void setCode( String strCode )
    {
        this._strCode = strCode;
    }

    /**
     * @return the Image
     */
    public File getImage( )
    {
        return _fileImage;
    }

    /**
     * @param _fileImage
     *            the Image to set
     */
    public void setImage( File _fileImage )
    {
        this._fileImage = _fileImage;
    }
}
