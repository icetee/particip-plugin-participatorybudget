<#if result.dynamicFields?? && result.dynamicFields.theme_text??>
	<#if     result.dynamicFields.theme_text = "Cadre de vie">                                                                             <#assign theme_class = "cadre_vie">
	<#elseif result.dynamicFields.theme_text = "Culture" || result.dynamicFields.theme_text = "Culture et patrimoine">                <#assign theme_class = "culture">
	<#elseif result.dynamicFields.theme_text = "Economie et emploi">                                                                       <#assign theme_class = "economie_emploi">
	<#elseif result.dynamicFields.theme_text = "Education et jeunesse">                                                                    <#assign theme_class = "education">
	<#elseif result.dynamicFields.theme_text = "Environnement" || result.dynamicFields.theme_text = "Nature en ville">                <#assign theme_class = "environnement">
	<#elseif result.dynamicFields.theme_text = "Logement et habitat">                                                                      <#assign theme_class = "logement">
	<#elseif result.dynamicFields.theme_text = "Participation citoyenne">                                                                  <#assign theme_class = "participation_citoyenne">
	<#elseif result.dynamicFields.theme_text = "Prévention et sécurité">                                                                   <#assign theme_class = "prevention_securite">
	<#elseif result.dynamicFields.theme_text = "Propreté">                                                                                 <#assign theme_class = "proprete">
	<#elseif result.dynamicFields.theme_text = "Solidarité et cohésion sociale" || result.dynamicFields.theme_text = "Vivre ensemble"><#assign theme_class = "solidarite">
	<#elseif result.dynamicFields.theme_text = "Sport">                                                                                    <#assign theme_class = "sport">
	<#elseif result.dynamicFields.theme_text = "Transport et mobilité">                                                                    <#assign theme_class = "transport">
	<#elseif result.dynamicFields.theme_text = "Ville intelligente et numérique">                                                          <#assign theme_class = "ville_numerique">
	</#if>
</#if>

<div class="col-xs-12 col-sm-4 col-md-4 pgagn-panel">

	<div class="pgagn-card bordered-4px-theme-${theme_class}">

		<a href="${result.url}" alt="${result.title}" title="${result.title}">
							
			<#-- Image et éventuel résultat du vote -->
			<#assign img_url = "images/local/skin/i_${theme_class}_1x2.png">
			<#if result.dynamicFields.image_text?has_content>
				<#assign img_url = result.dynamicFields.image_text?split("src=\"")[1]?split("\"")[0]>
			</#if>
			<div class="pgagn-card-img" style="background-image:url(${img_url!})">
				<#if false>
					<#if result.dynamicFields.statut_project_text=="GAGNANT" || result.dynamicFields.statut_project_text=="SUIVI" >
						<p class="winner">Projet gagnant<br>avec ${result.dynamicFields.nombre_de_votes_long?string.number!''} votes</p>
					<#elseif result.dynamicFields.statut_project_text=="PERDANT">
						<p class="looser">${result.dynamicFields.nombre_de_votes_long?string.number!''} votes</p>
					</#if>
				</#if>
			</div>

			<#-- Thématique -->
			<div class="pgagn-card-theme bgcolor-theme-${theme_class}">
				<span>${result.dynamicFields.theme_text!"Sans thématique"}</span>
			</div>

			<#-- Location -->
			<div class="pgagn-card-loc">
				<img src="images/local/skin/marker-${theme_class}.png" alt="" title="">
				<#if result.dynamicFields?? && result.dynamicFields.location_text??>
					<#if result.dynamicFields.location_text = "whole_city">${result.dynamicFields.location_text}
					<#else>${result.dynamicFields.location_text}
					</#if>
				</#if>
			</div>

			<#-- Titre -->
			<div class="pgagn-card-titre">
				<h3>
					<span><#if result.dynamicFields.code_projet_long?has_content && result.dynamicFields.code_projet_long &gt; 0 >${result.dynamicFields.code_projet_long} - </#if>${result.title}</span>
				</h3>
			</div>

		</a>
					
		<#-- Résultat du vote -->
		<div class="pgagn-card-result">
			<#if result.dynamicFields?? && result.dynamicFields.nombre_de_votes_long??>
				<!-- i class="fa fa-check"></i>&nbsp; -->
				<span><strong>${result.dynamicFields.nombre_de_votes_long?string.number!''}</strong> votes</span>
			</#if>
		</div>

		<#-- Pastilles et coût -->
		<div class="pgagn-card-footer">
			<div class="budget pull-right">
				<img src="images/local/skin/money-${theme_class}.png" width="25px" height="25px" alt="" title="">
				<#if result.dynamicFields??>
					<#if result.dynamicFields.budget_long?? && result.dynamicFields.budget_long &gt; 0>
						<#setting locale="fr_FR">
						${result.dynamicFields.budget_long?string("#,###")} &#8364;
					<#else>
						${result.dynamicFields.budget_text!""}
					</#if>
				<#else>
					-
				</#if>
			</div>
		</div>

	</div>
	
</div>