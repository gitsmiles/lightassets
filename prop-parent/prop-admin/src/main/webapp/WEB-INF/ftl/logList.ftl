<@c.html title="Table">
<#escape x as x?html>
<script type="text/javascript">
$(function(){
$(".datatable tr").mouseover(function() {$(this).addClass("over");}).mouseout(function() {$(this).removeClass("over");});
$(".datatable tr:even").addClass("alt");
});
</script>
<script type="text/javascript">
  $(function(){
     $.ba.datepinker($('#beginTime'));
     $.ba.datepinker($('#endTime'));
     
     $("#allbox").bind("click",function(){
        if($(this).attr("checked") == true){
           $("[type=checkbox]").attr("checked",true);
        }else{
           $("[type=checkbox]").attr("checked",false);
        }
     });
  });
</script>
<form action="${base}/log!findByModel" method="get">
<table>
	<tr>
	   <td style="HEIGHT: 26px">
         <span id="Label9">起始时间</span>
         <input name="beginTime" id="beginTime" type="text" value="${model.beginTime!}"/>
         <span id="Label8">终止时间</span>
         <input name="endTime" id="endTime"  type="text" value="${model.endTime!}"/>
         <span id="Label7" class="label">操作员名</span>
         <input name="operatorName" type="text" id="operatorName" value="${model.operatorName!}"/>
         <span id="Label13">操作员ID</span>
         <#if model.operatorId !=0>
         <input name="operatorId" type="text" id="operatorId" value="${model.operatorId!}"/>
         <#else>
         <input name="operatorId" type="text" id="operatorId" value=""/>
         </#if>
         <span id="Label13">事件类型</span>
         <select name="event" id="event">
            <option value="">不限</option>
	        <option <#if model.event?? && model.event == 'add'> selected="selected" </#if> value="add">增加</option>
	        <option <#if model.event?? && model.event == 'delete'> selected="selected" </#if> value="delete">删除</option>
	        <option <#if model.event?? && model.event == 'update'> selected="selected" </#if> value="update">修改</option>
	        <option <#if model.event?? && model.event == 'import'> selected="selected" </#if> value="import">导入</option>
	        <option <#if model.event?? && model.event == 'export'> selected="selected" </#if> value="export">导出</option>
	     </select>
	     <input name="query" type="submit" id="query"/>
       </td>
    </tr>
</table>
</form>
<form id="delform" action="${base}/log!deletLog" method="post">
<table id= "itemTable" class="datatable">
  <thead>
    <tr>
      <th>事件</th>
      <th>操作员名</th>
      <th>操作员ID</th>
      <th>原内容</th>
      <th>新内容</th>
      <th>时间</th>
    </tr>
  </thead>
  <tbody>
<#if page??>
  <#list page.pageList as log>
     <tr>
        <td>${log.event!}</td>
        <td>${log.operatorName!}</td>
        <td>${log.operatorId!}</td>
        <td>${log.oldContent!}</td>
        <td>${log.newContent!}</td>
        <td>${log.createAt!}</td>
     </tr>
  </#list>
</#if>
  </tbody>
</table>
</form>
<div class="page">
      <#import "/com/fost/webcommon/ftl/pager/pagination.ftl" as com>
           <#--前一个参数是totalCount总记录数，后一个参数是pageSize页面记录数-->  
	  <@com.pagination "${page.recordSum}" "${page.pageSize}"/>
 </div>
 </#escape>
</@c.html>
