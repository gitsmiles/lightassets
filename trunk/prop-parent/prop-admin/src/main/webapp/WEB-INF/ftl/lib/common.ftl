<#macro html title>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title}</title>
<#include "../common/css/basic.ftl">
<#include "../common/css/plugin.ftl">
<#include "../common/js/jquery.ftl">
<#include "../common/js/bgiframe.ftl">
<#include "../common/js/ba.ftl">
<#include "../common/js/formvalidator.ftl">
<#include "../common/js/datepicker.ftl">
<#include "../common/js/treeview.ftl">
<style type="text/css">
.required {
  color: red;
  margin: 0 4px;
}

#cardInfo table {
  margin: 0;
  padding: 0;
}

#cardInfo table tr {
  margin: 0;
  padding: 0;
}

#cardInfo table td {
  margin: 0;
  padding: 3px;
  width: 2em;
  text-align: center;
}

#cardInfo table td input {
  width: 2em;
}
</style>
</head>
<body class="body_pad10">
<#nested />
</body>
</html>
</#macro>
