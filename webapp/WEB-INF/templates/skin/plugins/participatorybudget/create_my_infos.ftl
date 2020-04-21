<#include "/skin/plugins/asynchronousupload/upload_commons.ftl" />
<script type="text/javascript" src="jsp/site/plugins/asynchronousupload/GetMainUploadJs.jsp?handler=campaignUploadHandler"></script>
<section class="bg-color6">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-offset-1 col-sm-10">
				<div id="mesinfos" class="content-padding bg-mesinfos">
					<h1 class="title1 centered">Création de compte</h1>
					<form class="form" role="form" method="post" action="jsp/site/Portal.jsp" enctype="multipart/form-data">
						<input type="hidden" name="page" value="mesInfos">
						<input type="hidden" name="action" value="doCreateInfos">
						<input type="hidden" name="token" value="${token}" id="token">
						<div class="form-group">
							<@messages errors=errors />
							<@messages infos=infos />
						</div>
						<div <#if campaignService.isDuring("VOTE") >style="display:none;"</#if>>
							<div class="form-group">
								<h3 class="form-control-static">Votre pseudo et votre avatar</h3>
								<em>Les champs marqu&eacute;s d'un ast&eacute;risque sont obligatoires.</em>
							</div>
							<div class="form-group">
								<label class="sr-only" for="budget-nickname">#i18n{participatorybudget.mes_infos.nickname} *</label>
								<input class="form-control" type="text" name="nickname" id="budget-nickname" value="${nickname!''}" placeholder="#i18n{participatorybudget.mes_infos.nickname} *" autofocus>
							</div>
						</div>
						<div class="form-group">
							<label for="avatar" class="sr-only">#i18n{participatorybudget.mes_infos.avatar} </label>
							<img class="img-circle" src="${avatar_url!''}" alt="" title="">
							<@addFileInputAndfilesBox fieldName="avatar_image" handler=handler listUploadedFiles=[] inputCssClass='form-control' multiple=false singleUpload=true/>
						</div>
						<div class="form-group">
							<h3 class="form-control-static">Votre adresse mail et votre mot de passe </h3>
							(Votre mot de passe doit contenir au moins 8 caractères)
						</div>
						<div class="form-group">
							<label class="sr-only" for="budget-login">#i18n{participatorybudget.mes_infos.login} *</label>
							<input class="form-control" type="text" name="login" id="budget-login" value="${login!''}" placeholder="#i18n{participatorybudget.mes_infos.login} *">
						</div>
						<div class="form-group">
							<label class="sr-only" for="budget-password">#i18n{participatorybudget.mes_infos.password}</label>
							<input class="form-control" type="password" name="password" id="budget-password" value="" aria-describedby="info-password" autocomplete="off" placeholder="#i18n{participatorybudget.mes_infos.password} *">
							<p id="info-password" class="help-block sr-only">Le mot de passe doit contenir au moins 8 caractères</p> 
						</div>
						<div class="form-group">
							<label class="sr-only" for="budget-confirmation-password">#i18n{participatorybudget.mes_infos.confirmationPassword} *</label>
							<input class="form-control" type="password" name="confirmation_password" id="budget-confirmation-password" value="" autocomplete="off" placeholder="#i18n{participatorybudget.mes_infos.confirmationPassword} *">
						</div>
						<div class="form-group">
							<h3 class="form-control-static">Vos informations personnelles</h3>
						</div>
						<div class="form-group">
							<#assign chk_civility_MME="" />
							<#assign chk_civility_M="" />
							<#assign chk_civility_NPR="" />
							<#if civility?? && civility=="MME">
								<#assign chk_civility_MME="checked" />
							<#elseif civility?? && civility=="M">
								<#assign chk_civility_M="checked" />
							<#else>
								<#assign chk_civility_NPR="checked" />
							</#if>
							<label for="budget-civility1" class="radio-inline">
								<input type="radio" name="civility" id="budget-civility1" value="MME" ${chk_civility_MME}>#i18n{participatorybudget.mes_infos.civilityMme}
							</label>
							<label for="budget-civility2" class="radio-inline">
								<input type="radio" name="civility" id="budget-civility2" value="M" ${chk_civility_M}>#i18n{participatorybudget.mes_infos.civilityM}
							</label>
							<label for="budget-civility3" class="radio-inline">
								<input type="radio" name="civility" id="budget-civility3" value="NPR" ${chk_civility_NPR}>#i18n{participatorybudget.mes_infos.civilityNPR}
							</label>
						</div>
						<div <#if campaignService.isDuring("VOTE") >style="display:none;"</#if>>
							<div class="form-group">
								<label class="sr-only" for="budget-lastname">#i18n{participatorybudget.mes_infos.lastname} </label>
								<input class="form-control" type="text" name="lastname" id="budget-lastname" value="${lastname!''}" placeholder="#i18n{participatorybudget.mes_infos.lastname} ">
							</div>
							<div class="form-group">
								<label class="sr-only" for="budget-firstname">#i18n{participatorybudget.mes_infos.firstname} </label>
								<input class="form-control" type="text" name="firstname" id="budget-firstname" value="${firstname!''}" placeholder="#i18n{participatorybudget.mes_infos.firstname} ">
							</div>
						</div>
						<div class="form-group">
							<label class="sr-only" for="budget-birthdate">#i18n{participatorybudget.mes_infos.birthdate} *</label>
							<input class="form-control" type="text" name="birthdate" id="budget-birthdate" value="${birthdate!''}" placeholder="#i18n{participatorybudget.mes_infos.birthdate}  *">
						</div>
						<div class="form-group">
							<label class="sr-only" for="budget-address">#i18n{participatorybudget.mes_infos.address} *</label>
							<div class="input-group">
								<input class="form-control" type="text" name="address" id="budget-address" value="${address!''}" placeholder="#i18n{participatorybudget.mes_infos.address} *">
								<span class="input-group-btn">
									<button class="btn btn-default btn-xs" type="button" title="effacer" id="button_delete_adress"><i class="fa fa-times"></i></button>
								</span>
							</div>
							<input id="geojson" type="hidden" name="geojson" value="${(geoJson?html)!''}">
							<input id="geojson_state" type="hidden">
						</div>
						<div class="form-group" <#if campaignService.isBeforeBeginning("SUBMIT") || campaignService.isAfterEnd("VOTE") >style="display: none;"</#if>>
							<h3>Vos informations de vote</h3>
							<div class="form-group">
								<img src="images/local/skin/i_vote_arr_green.png" class="logo" style="margin-right:5px; float:left;">
								<p>Vous ne pouvez voter que pour des projets <i>Tout Paris</i>, et des projets localis&eacute;s dans votre <strong><i>arrondissement de vote</i></strong>. Ce dernier peut correspondre &agrave; votre arrondissement de r&eacute;sidence, de travail, de c&oelig;ur...</p>
								<label  for="arrondissement" class="sr-only" >#i18n{participatorybudget.mes_infos.arrondissement} *</label>
								<@comboWithParams name="arrondissement" items=area_list default_value="${arrondissement!''}" additionalParameters="class='form-control'" />
							</div>
						</div>
						<br>
						<div class="form-group">
							<div class="checkbox">
								<label for="budget-sendaccountvalidation">
									<input type="checkbox" name="sendaccountvalidation" id="budget-sendaccountvalidation" style="display:none" checked="checked">
								</label>
							</div>
						</div>
						<div class="form-group">
							<div class="checkbox">
								<label for="budget-iliveinparis">
									<#assign chk_iliveinparis="" />
									<#if iliveinparis?? && ( iliveinparis=="on" ||iliveinparis=="default" )>
										<#assign chk_iliveinparis="checked" />
									</#if>
									<input type="checkbox" name="iliveinparis" id="budget-iliveinparis" ${chk_iliveinparis}> #i18n{participatorybudget.mes_infos.cb.iLiveInParis} *
								</label>
							</div>
						</div>
						<div class="form-group">
							<div class="checkbox">
								<label for="budget-stayconnected">
								<#assign chk_stayconnected="" />
								<#if (stayconnected?? &&  stayconnected=="on")|| (init_form?? && init_form)>
									<#assign chk_stayconnected="checked" />
								</#if>
								<input type="checkbox" name="stayconnected" id="budget-stayconnected" ${chk_stayconnected}> #i18n{participatorybudget.mes_infos.cb.stayConnected}
							</label>
							</div>
						</div>
						<#if captcha?exists>
							<div class="form-group">
							${captcha}
							</div>
						</#if>
						<div class="form-group text-center">
							<button class="btn btn-lg btn-lt-bg" type="submit">#i18n{participatorybudget.mes_infos.bt.save}</button>
							<#if completeInfos?? && completeInfos && from_url?? && from_url !=''>
								<a class="btn btn-primary" href="${from_url!}" title="#i18n{portal.util.labelBack}">
									#i18n{portal.util.labelBack}
								</a>
							</#if>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="js/proj4js-combined.js"></script>
<script type="text/javascript" src="jsp/site/plugins/address/modules/autocomplete/SetupSuggestPOI.js.jsp"></script>
<script type="text/javascript" src="js/plugins/address/modules/autocomplete/jQuery.suggestPOI.js"></script>
<script type="text/javascript" src="js/jquery/plugins/inputmask/jquery.inputmask.bundle.min.js"></script>
<script type='text/javascript'>
	$("#budget-nickname").focus()
    $(document).ready(function () {
	  /* Ajout mask saisie */
	  $("#budget-birthdate").inputmask('dd/mm/yyyy',{ "placeholder": "jj/mm/aaaa" });
      /*suggestpoi*/
      initMyInfoAddressAutoComplete();
    });
</script>