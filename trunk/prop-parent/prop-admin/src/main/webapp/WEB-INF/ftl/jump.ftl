<#import "/com/fost/uum/macro/current_user.ftl" as current_user />
<#if childList??>
  <#list childList as node>
     <#if current_user.hasRights((node.id!).toString()) || node.getParentId() != 1>
     <li id="node_${node.id!}">
        <span class="folder" style="float: left;" id="span_${node.id!}"><a href="item-list?nodeId=${node.id!}" target="myFrame">${node.name!}</a></span>
      <#if current_user.hasRights("node_add")>
         <a href="#" id="add_${node.id}" title="添加子项">[+]</a>
      </#if>
      <#if current_user.hasRights("node_update")>
         <a href="#" id="edit_${node.id}" title="编辑该项">[∷]</a>
      </#if>
      <#if current_user.hasRights("node_delete")>
         <a href="#" id="del_${node.id}" title="删除本项">[-]</a>
      </#if> 
      <span style="clearleft">&nbsp;</span>       
        <ul id="ul_${node.id!}"></ul>
     </li>
     </#if>
  </#list>
</#if>
