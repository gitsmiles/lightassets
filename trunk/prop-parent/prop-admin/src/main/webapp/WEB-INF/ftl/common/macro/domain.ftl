<!--根域名端口设置-->

<#assign adminbasedomain=stack.findValue("@com.fost.webcommom.site.SiteConfig@getConfig('adminbasedomain')") >

<#macro domain>${adminbasedomain}</#macro>

<!--静态资源-->
<#macro static>http://static.<@domain/></#macro>