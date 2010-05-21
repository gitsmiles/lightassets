<@c.html title="Table">
<#escape x as x?html>
<script type="text/javascript">
$(function() {
  $(".datatable tr").mouseover(function() {$(this).addClass("over");}).mouseout(function() {$(this).removeClass("over");});
  $(".datatable tr:even").addClass("alt");
});
</script>
<table>
  <tr>
     <td>
                  操作成功！
     </td>
  </tr>
</table>
</#escape>
</@c.html>
