<jsp:useBean id="voteParArrd" scope="session" class="fr.paris.lutece.plugins.budgetparticipatif.web.VoteParArrandJspBean" />
<% String strContent = voteParArrd.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>

