<%@ attribute name="value"
    rtexprvalue="true"
    required="true"
    type="java.util.Date" %>

<%
    java.util.Locale locale = (java.util.Locale) session.getAttribute("locale");

    java.text.SimpleDateFormat formatDta;
    if (locale.getCountry().equals("UA")){
        formatDta = new java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm", locale);
    } else {
        formatDta = new java.text.SimpleDateFormat("MMMM dd, yyyy, HH:mm a", locale);
    }
    java.lang.String date = formatDta.format(jspContext.getAttribute("value"));
    out.print(date);
%>