<#import "/com/fost/uum/macro/current_user.ftl" as current_user />
<@c.html title="修改属性">
<#escape x as x?html>
<script type="text/javascript">
$(function() {
$(".datatable tr").mouseover(function() {$(this).addClass("over");}).mouseout(function() {$(this).removeClass("over");});
$(".datatable tr:even").addClass("alt");
});
</script>
<script type="text/javascript">
  $(function() {
     $("#upbutton").click(function(){
        $("#upForm").submit();
     });
  });
</script>
<table>
   <tr>
     <td></td>
   </tr>
   <tr>
     <td><h1 style="padding:0 0 10px 0;">当前路径：参数管理/${currPath!}</h1></td>
   </tr>
    <tr>
     <td></td>
   </tr>  
   <tr>
      <td>
        <form id="upForm" action="${base}/item-list!uploadFile" method="post" enctype="multipart/form-data">
          <#if current_user.hasRights("attr_import")>
            <input type="hidden" name="nodeId" value="${nodeId}"/>
            <input type="file" name="upload" size="20"/> <a href="#" id="upbutton" class="form_btn">导 入</a>
          </#if>
          <#if current_user.hasRights("attr_export")>
            <a href="${base}/item-list!downLoadFile?nodeId=${nodeId!}" class="form_btn">导 出</a>
          </#if>
          <#if current_user.hasRights("attr_add")>
            <a href="${base}/attribute/new?nodeId=${nodeId!}" class="form_btn">新增</a>
          </#if>
        </form>
      </td>
   </tr>
</table>
<table id= "itemTable" class="datatable">
  <thead>
    <tr>
      <th>key</th>
      <th>value</th>
      <th>value1</th>
      <th>value2</th>
      <th>value3</th>
      <th>value4</th>
      <th>value5</th>
      <th>备注</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
<#if attrList??>
  <#list attrList as attr>
     <tr>
        <td>${attr.key!}</td>
        <td>${attr.value!}</td>
        <td>${attr.value1!}</td>
        <td>${attr.value2!}</td>
        <td>${attr.value3!}</td>
        <td>${attr.value4!}</td>
        <td>${attr.value5!}</td>
        <td>${attr.memo!}</td>
        <td>
        <#if current_user.hasRights("attr_delete")>
           <a href="attribute/${attr.id!}?_method=DELETE&amp;nodeId=${nodeId!}">删除</a>
        </#if>
        <#if current_user.hasRights("attr_update")>
           <a href="attribute/${attr.id!}/edit?nodeId=${nodeId!}">编辑</a>
        </#if>
           <a href="attribute!moveUp?nodeId=${nodeId!}&amp;attributeId=${attr.id!}">↑</a>
           <a href="attribute!moveDown?nodeId=${nodeId!}&amp;attributeId=${attr.id!}">↓</a>
        </td>
     </tr>
  </#list>
</#if>
  </tbody>
</table>
</#escape>
</@c.html>
