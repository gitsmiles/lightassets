<#import "/com/fost/uum/macro/current_user.ftl" as current_user />
<@c.html title="参数服务">
<#escape x as x?html>
<script type="text/javascript">
  $(function(){
    $("#ul_0").treeview({
      collapsed:true,
        toggle: function(e){
        var oid= $(this).attr("id")
        var pid= oid.substr(oid.indexOf("_")+1,oid.length);
        if ($('#ul_'+pid).children().size()<1){
          doAppend(pid);
        }
        if ($("#li_"+pid+"_temp").length>0){
          $("#li_"+pid+"_temp").remove();
        }
        }
    });
    bindAllBtn();
  });

  function bindAllBtn(){
    bindAddBtn() ;
    bindEditBtn() ;
    bindDelBtn() ;
  }

  function bindAddBtn(){
    $("a[id^='add']").each(function(i){
      var obj=$("a[id^='add']").eq(i);
      obj.unbind('click');
      obj.click(function(i){
        var oid= obj.attr("id")
        var pid= oid.substr(oid.indexOf("_")+1,oid.length);
        if ($("#li_"+pid+"_temp").length<=0){
          var branches = $("<li id='li_"+pid+"_temp'><span class='folder'  id='span_"+pid+"_temp' ><input type='text' value='' id='input_"+pid+"_temp' name='input_"+pid+"_temp' size=15><a href='#' id='save_"+pid+"_temp' onclick=\"javascript:doSave('"+pid+"');\" >保存</a></span></li>").appendTo($('#ul_'+pid));
          $("#ul_0").treeview({add:branches});
        }
        });
    }) ;
  }

  function bindDelBtn(){
    $("a[id^='del']").each(function(i){
      var obj=$("a[id^='del']").eq(i);
      obj.unbind('click');
      obj.click(function(i){
        var oid= obj.attr("id");
        var nid= oid.substr(oid.indexOf("_")+1,oid.length);
        doDelete (nid);
      });
    });
   }

  function bindEditBtn(){
    $("a[id^='edit']").each(function(i){
      var obj=$("a[id^='edit']").eq(i);
      obj.unbind('click');
      obj.click(function(i){
        var oid= obj.attr("id")
        var nid= oid.substr(oid.indexOf("_")+1,oid.length);
        doEdit(nid) ;
      });
    });
  }

  function doAppend(pid){
    $.get("appent?id="+pid,function(data){
      if ($.trim(data).length>0){
        var branches= $('#ul_'+pid).html($(data));
        $("#ul_0").treeview({add:branches});
        bindAllBtn();
      }
    });
  }

  function doSave(pid){
    var nodeValue=$("#input_"+pid+"_temp").val();
    $.post("node?parentId="+pid+"&name="+encodeURIComponent(nodeValue),function(data){
      $("#li_"+pid+"_temp").remove();
      var branches=$(data).appendTo($('#ul_'+pid));
      $("#ul_0").treeview({add:branches});
       bindAllBtn();
     });
  }

  function doDelete(nid){
    if (confirm("确定要删除吗？")){
      $.post("node/"+nid+"?_method=DELETE",function(data){
        if ($.trim(data)=="0"){
          alert ("删除失败，该节点早已不存在，点击确定刷新列表");
          }else{
            $("#node_"+nid).remove();
          }
        });
    }
  }

  function doEdit(nid){
    $.getJSON("node/"+nid+"/edit",function(data){
      if (data.result == 1) {
        var row = data.target
        var name=row.name;
        $("#span_"+nid).html("<input type='text' value='"+name+"' id='inputnode_"+nid+"_temp' name='input_"+nid+"_temp' size=15><a href='#' id='save_"+nid+"_temp' onclick=\"javascript:doUpdate('"+nid+"');\" >保存</a>");
        $("#add_"+nid).hide();
        $("#edit_"+nid).hide();
        $("#del_"+nid).hide();
        } else {
          alert("该节点已不存在，点击确定刷新列表");
          $("#node_" + nid).remove();
        }
      });
  }

  function doUpdate(nid){
    var nodeValue=$("#inputnode_"+nid+"_temp").val();
    $.post("node/"+nid+"?_method=PUT"+"&name="+encodeURIComponent(nodeValue),function(data){
      if ($.trim(data)=="0"){
        alert ("该节点已不存在，点击确定刷新列表");
        $("#node_"+nid).remove();
      }else{
        $("#span_"+nid).html(data);
        $("#add_"+nid).show();
        $("#edit_"+nid).show();
        $("#del_"+nid).show();
        bindAllBtn() ;
      }
    });
  }
</script>

<ul id="ul_0" class="filetree">
 <#if childList??>
   <#list childList as node>
    <li id="node_${node.id}">
      <span class="folder" style="float: left;" id="span_${node.id}"><a href="item-list?nodeId=${node.id}" target="myFrame">${node.name}</a></span>
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
      <ul id="ul_${node.id}"></ul>
    </li>
   </#list>
 </#if>
</ul>
</#escape>
</@c.html>
