<#if !(sort_name??)>
  <#assign sort_name="nombre_de_votes_long">
</#if>
<#if !(sort_order??)>
  <#assign sort_order="desc">
</#if>
<#if conf??>
  <#assign conf_query= "conf="+conf.code>
</#if>
<#assign facet_theme = "tout_theme">
<#assign facet_campaign = "toute_campaign">
<#setting url_escaping_charset=encoding>
<#-- Encode facet queries -->
<#assign allParisCity = "-location_text:whole_city">
<#assign allArrParisCity = "location_text:">
<#macro EncodeFQ list_fq  optionalParam1="" optionalParam2="">
  <#assign bCheck = false>
    <#assign encoded_facets_queries = "">
    <#list list_fq as facet>
    <#if optionalParam1?has_content && optionalParam2?has_content && facet?starts_with(optionalParam2) >
      <#assign encoded_facets_queries = encoded_facets_queries+"&fq="+optionalParam1>
      <#assign bCheck = true>
    <#else>
      <#assign encoded_facets_queries = encoded_facets_queries+"&fq="+facet?url>
      <#if facet?starts_with(optionalParam1)>
        <#assign bCheck = true>
      </#if>
    </#if>
    </#list>
    <#if optionalParam1?has_content && optionalParam2?has_content && !bCheck>
        <#assign encoded_facets_queries = encoded_facets_queries+"&fq="+optionalParam1>
    </#if>
${encoded_facets_queries}</#macro>
<#assign sQuery = "">
<#macro EncodeFQArrondissement list_fq >
    <#assign encoded_facets_queries = "">
    <#list list_fq as facet>
  <#if facet?starts_with("location_text") || facet?starts_with("type") || facet?starts_with("-location_text")>
          <#assign encoded_facets_queries = encoded_facets_queries+"&fq="+facet?url>
  </#if>
    </#list>
${encoded_facets_queries}
</#macro>
<#if query?has_content>
    <#if !query?starts_with("categorie")>
        <#if !query?starts_with("(")>
            <#assign sQuery = "${query}">
        <#else>
            <#assign sQuery = "${query?split(' AND')?first?substring(1, query?split(' AND')?first?length)}">
        </#if>
    </#if>
</#if>
 <#assign sQueryUrlSave = sQuery>
 <#assign sQuery = sQuery?split(":")?last>
 <#assign arr= "">
 <#assign monTri ="&amp;sort_order=${sort_order!}&amp;sort_name=${sort_name!}">
 <#assign ordre_aleatoire= "true">
 <#assign sQueryUrl= sQuery>
<#assign cadre_de_vie= 'false'>
<#assign education_et_jeunesse= 'false'>
<#assign logement_et_habitat= 'false'>
<#assign environnement= 'false'>
<#assign sport= 'false'>
<#if sQuery?has_content && sQuery?starts_with("(") && sQuery?ends_with(")") >
  <#assign sQuery = sQuery?remove_beginning("(")>
  <#assign sQuery = sQuery?remove_ending(")")>
    </#if>
    <#-- facets -->
     <#assign number_random="${.now?long?string}"?number />
	 
	 
<div id="pgagn-container" class="bg-color6">

	<#-- Titre -->

	<div id="pgagn-header" class="container-fluid">
		<div id="pgagn-header-title">
			<h1>Les&nbsp;projets lauréats</h1>
		</div>
		<div id="pgagn-header-links" class="row">
			<div class="col-sm-4 col-md-3 col-sm-offset-2 col-md-offset-3"><a href="jsp/site/Portal.jsp?page=search-solr&conf=elected_projects#laureat_tout_paris">Tout Paris&nbsp;&gt;</a></div>
			<div class="col-sm-4 col-md-3 "><a href="jsp/site/Portal.jsp?page=search-solr&conf=elected_projects#laureat_arrdt"     >Par arrondissement&nbsp;&gt;</a></div>
		</div>
		<div id="pgagn-header-btns" class="row">
			<div class="col-md-6"><a class="btn btn-16rem btn-black-on-white" target="_blank" href="plugins/download/BP2018-DossierDePresse.pdf">Télécharger le dossier de presse</a></div>
			<div class="col-md-6"><a class="btn btn-16rem btn-black-on-white" target="_blank" href="https://capgeo.maps.arcgis.com/apps/webappviewer/index.html?id=9274f72a36b94149855a6bd8baca30fc">Voir la carte des lauréats</a></div>
		</div>
	</div>

	<#-- Liste des projets -->
	
	<div id="pgagn-winner" class="container">
		<form id="searchForm" name="search" method="get" action="jsp/site/Portal.jsp#laureat_arrdt" class="form-inline">
			<input type="hidden" name="page" value="search-solr">
			<input type="hidden" name="sort_name" id="sort_name" value="${sort_name!}">
			<input type="hidden" name="sort_order" id="sort_order" value="${sort_order!}">
			<input id="form_conf_hidden" type="hidden" name="conf" value="elected_projects">

			<#-- Gestion du champ de recherche sur l'arrondissement -->
			
			<#if facets_list??>
				<#list facets_list as facet>
					<#if facet?starts_with("location_text:")>
						<#assign arr = facet?split(":")?last>
					<#elseif facet?starts_with("-location_text:whole_city")>
						<#assign arr = "all_arr">
					<#elseif facet?starts_with("location_text:whole_city")>
						<#assign arr = "whole_city">
					<#elseif facet?starts_with("theme_text:")>
						<#assign facet_theme = facet?split(":")?last>
					<#elseif facet?starts_with("campaign_text:")>
						<#assign facet_campaign = facet?split(":")?last>
					</#if>
				</#list>
			</#if>
			
			<div id="pgagn-list">

				<#-- Projets TOUT PARIS -->
				<div id="laureat_tout_paris" class="winner">
					<h2>Les laur&eacute;ats &laquo; Tout Paris &raquo;</h2>
					
					<#assign theme_class = "autre">
					<#if projectLaureatToutParis??>
						<div class="row">
							<#list projectLaureatToutParis as result>
								<#include "document_laureats_sous_list_solr_search_results.ftl">
							</#list>
						</div>
					</#if>
				</div>

				<#-- Projets ARRONDISSEMENT -->
				
				<div id="laureat_arrdt" class="winner">
					<h2>Les laur&eacute;ats par arrondissement
						<label class="select" for="arrondissement">
							<select name="fq" id="arrondissement" class="form-control">
								<#if arr=="Tout arrd">
									<option id="toutArrdt" value='-location_text:whole_city' selected="selected">Tous les arrondissements</option>
								<#else>
									<option id="toutArrdt" value='-location_text:whole_city'>Tous les arrondissements</option>
								</#if>
								<#if arr=="1e arrondissement">
									<option id="1erArrond" value="location_text:1e arrondissement" selected="selected">1er arrondissement</option>
								<#else>
									<option id="1erArrond" value="location_text:1e arrondissement">1er arrondissement</option>
								</#if>
								<#assign nbArr=20>
								<#list 2..nbArr as i>
									<#if arr==i+"e arrondissement">
										<option value="location_text:${i!}e arrondissement" selected="selected" >${i!}e arrondissement</option>
									<#else>
										<option value="location_text:${i!}e arrondissement">${i!}e arrondissement</option>
									</#if>
								</#list>
							</select>
						</label>
					</h2>
					
					<#assign theme_class = "autre">
					<div class="row">
						<#list results_list as result>
							<#if result.dynamicFields?? && result.dynamicFields.location_text?? && result.dynamicFields.location_text != "whole_city" >
								<#include "document_laureats_sous_list_solr_search_results.ftl">
							</#if>
						</#list>
					</div>
				</div>

			</div>
			
			<div class="row">
				<div class="col-xs-12 col-sm-12">
					<div class="container">
						<@paginationSolr paginator=paginator />
					</div>
				</div>
			</div>
			
		</form>
		
	</div>
	
	<div class="row">
		<div id="project-sharing-buttons" class="col-xs-12 col-sm-12 col-md-12 text-center hidden-print">
			<h2 class="title2">Je Partage !</h2>
			<a href="http://www.facebook.com" data-action="facebook" class="share" title="Partager sur Facebook">
				<img src="images/local/skin/projet_logo_facebook.png" alt="Partager sur Facebook" title="Partager sur Facebook"/>
			</a>&#160;&#160;
			<a href="http://www.twitter.com" data-action="twitter" class="share" title="Partager sur Twitter" target="_blank">
				<img src="images/local/skin/projet_logo_twitter.png" alt="Partager sur Twitter" title="Partager sur Twitter"/>
			</a>&#160;&#160;
		</div>
	</div>
	
</div>

<#-- Freemarker macros -->
<#-- Number of items per page selector - Combo Box implementation -->
<#macro NbItemsPerPageSelectorCombo nb_items_per_page>
    <select name="items_per_page">
        <#list [ "10" , "20" , "50" , "100" ] as nb>
            <#if nb_items_per_page = nb >
                <option selected="selected" value="${nb}">${nb}</option>
            <#else>
                <option value="${nb}">${nb}</option>
            </#if>
        </#list>
    </select>
</#macro>
<#-- Number of items per page selector - Radio List implementation -->
<#macro NbItemsPerPageSelectorRadioList nb_items_per_page>
    <#list [ 5 , 10 , 20 , 50 ] as nb>
    <#if nb = nb_items_per_page >
    <input value="${nb}" id="items_per_page${nb}" name="items_per_page" class="radio" type="radio" checked /><label for="items_per_page${nb}">${nb}</label>
    <#else>
    <input value="${nb}" id="items_per_page${nb}" name="items_per_page" class="radio" type="radio" /><label for="items_per_page${nb}">${nb}</label>
    </#if>
    </#list>
</#macro>
<#macro paginationSolr paginator >
  <#assign nbLinkPages = 3 />
  <#assign offsetPrev = nbLinkPages/2 />
  <#assign offsetNext = nbLinkPages/2 />
  <#if paginator.pageCurrent &lt; nbLinkPages-offsetPrev>
    <#assign offsetPrev = nbLinkPages - paginator.pageCurrent />
    <#assign offsetNext = nbLinkPages - offsetPrev />
  <#elseif paginator.pageCurrent &gt; paginator.pagesCount - (nbLinkPages - offsetNext) >
    <#assign offsetNext = nbLinkPages - (paginator.pagesCount - paginator.pageCurrent) />
    <#assign offsetPrev = nbLinkPages - offsetNext />
  </#if>
  <br>
  <#if (paginator.pagesCount > 1) >
    <#if paginator.pageCurrent &gt; nbLinkPages-offsetPrev >
      <a class="paginator-navi-page" href="${paginator.firstPageLink?xhtml}${monTri}#laureat_arrdt" title="Première page">
        <span class="show-xs"></span><i class="fa fa-angle-double-left"></i>
      </a>
    </#if>
    <#if (paginator.pageCurrent > 1) >
     <a class="paginator-navi-page"  href="${paginator.previousPageLink?xhtml}${monTri}#laureat_arrdt" title="Page précédente">
       <span class="show-xs"></span><i class="fa fa-angle-left"></i>
     </a>
    </#if>
    <#if paginator.pageCurrent &gt; nbLinkPages-offsetPrev >
     <span class="paginator-sep-page">...</span>
    </#if>
    <#list paginator.pagesLinks as link>
      <#if ( link.index == paginator.pageCurrent )>
        <span class="paginator-current-page">${link.name}</span> <span class="paginator-sep-page">|</span>
      <#else>
        <a class="paginator-num-page" href="${link.url?xhtml}${monTri}#laureat_arrdt" title="Aller à la page ${link.name}">${link.name}</a> 
		<span class="paginator-sep-page">|</span>
      </#if>
    </#list>
    <#if paginator.pageCurrent &lt; paginator.pagesCount - (nbLinkPages-offsetNext) >
      <span class="paginator-sep-page">...</span>
    </#if>
    <#if (paginator.pageCurrent < paginator.pagesCount) >
      <a class="paginator-navi-page"  href="${paginator.nextPageLink?xhtml}${monTri}#laureat_arrdt" title="Page suivante">
        <span class="show-xs"></span><i class="fa fa-angle-right"></i>
      </a>
      <#if paginator.pageCurrent &lt; paginator.pagesCount - (nbLinkPages-offsetNext) >
        <a class="paginator-navi-page" href="${paginator.lastPageLink?xhtml}${monTri}#laureat_arrdt" title="Dernière page">
          <span class="show-xs"></span><i class="fa fa-angle-double-right"></i>
        </a>
      </#if>
    </#if>
  </#if>
</#macro>
<script type="text/javascript">
function searchTri(){
  $("#searchForm").submit();
}
function validateForm(form) {
    var all_ok=true;
    //From xss filter + lucene special characters
    var forbidden_chars = "<>#&|():~[]\\\"?*{}^+!";
    $(form).find("input[type=text]").each(function(idx, elem) {
    var error = false;
    for (var i = 0; i < forbidden_chars.length; i++) {
        if (!error && elem.value.indexOf(forbidden_chars.charAt(i)) != -1) {
            error = true;
            all_ok = false;
            var parent = $(elem).parents(".form-group");
            parent.addClass("has-error has-feedback");
            var helpblock = $(parent).find(".help-txt");
            helpblock.after("<p class=\"help-block\"><i class=\"fa fa-warning\"></i> Les caract&egravesres '" + forbidden_chars + "' sont interdits.</p>");
            helpblock.remove();
          }
        }
        if (!error) {
          var str = elem.value;
          var prev_char = " ";
          for(var i=0; i<str.length;i++) {
            if (str[i] === "-" && prev_char === " ") {
              error = true;
              all_ok = false;
              var parent = $(elem).parents(".form-group");
              parent.addClass("has-error has-feedback");
              var helpblock = $(parent).find(".help-txt");
              helpblock.after("<p class=\"help-block\"><i class=\"fa fa-warning\"></i> Les caract&egravesres '" + forbidden_chars + "' sont interdits.</p>");
              helpblock.remove();
              break;
            }
            prev_char=str[i];
          }
        }
    });
    if (!all_ok) {
      $(form).find("button").removeAttr("disabled");
    }
  return all_ok;
}
$(function(){
  document.title = "Projets Lauréats - Budget Participatif - Paris";
  $("#searchForm").submit( function(){
    return validateForm( $(this) );
  })
  $("select").change( function(){
    searchTri();
  })
  var arrond = $("#defaultArrond").val();
  if ( arrond ){
   var numberArrond = arrond.substr(3,4);
   var x = location.search;
      if ( !x.match("location_text") ){
        $("#arrondissement option").eq( numberArrond ).attr('selected','selected');
	$("#searchForm").get(0).setAttribute('action', 'jsp/site/Portal.jsp');
        searchTri();
      }
  }
});
</script>