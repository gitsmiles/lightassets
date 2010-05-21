<@c.html title="修改属性">
<#escape x as x?html>
<script type="text/javascript">
$(function() {
  $("button").hover(function() {
    $(this).addClass("form_btn_hover");
  }, function() {
    $(this).removeClass("form_btn_hover");
  });
});

function subform() {
  if ($("#mykey").val() == "") {
    alert("key不可以为空");
    return;
  } else if ($("#myvalue").val() == "") {
    alert("value不可以为空");
    return;
  }
  $("#form1").submit();
}

function cancel() {
  location.href = "${base}/item-list?nodeId=" + ${nodeId};
}
</script>
<form id="form1" name="form1" action="${base}/attribute/${id}" method="post" class="forms forms_style">
  <input type="hidden" name="_method" value="put" />
  <input type="hidden" name="nodeId" value="${nodeId}" />
  <input type="hidden" name="sorting" value="${sorting}" />
  <fieldset>
    <h3><span><b>修改属性</b></span></h3>
  </fieldset>
  <table class="formtable">
     <tr>
        <th><label><span><b>*</b>key：</span></label></th>
        <td><input id="mykey" name="key" type="text" value="${key!}" size="80" maxlength="80" /></td>
     </tr>
     <tr>
        <th><label><span><b>*</b>value：</span></label></th>
        <td><textarea id="myvalue" name="value" cols="60">${value!}</textarea></td>
     </tr>
     <tr>
        <th><label><span>value1：</span></label></th>
        <td><textarea id="myvalue1" name="value1" cols="60">${value1!}</textarea></td>
     </tr>
     <tr>
        <th><label><span>value2：</span></label></th>
        <td><textarea id="myvalue2" name="value2" cols="60">${value2!}</textarea></td>
     </tr>
     <tr>
        <th><label><span>value3：</span></label></th>
        <td><textarea id="myvalue3" name="value3" cols="60">${value3!}</textarea></td>
     </tr>
     <tr>
        <th><label><span>value4：</span></label></th>
        <td><textarea id="myvalue4" name="value4" cols="60">${value4!}</textarea></td>
     </tr>
     <tr>
        <th><label><span>value5：</span></label></th>
        <td><textarea id="myvalue5" name="value5" cols="60">${value5!}</textarea></td>
     </tr>
     <tr>
        <th><label><span>备注：</span></label></th>
        <td><textarea id="mymemo" name="memo" cols="60">${memo!}</textarea></td>
     </tr>
  </table>
  <div style="padding: 20px 0">
    <a href="#" onclick="subform()" class="form_btn">确 定</a>
    <a href="#" onclick="cancel()" class="form_btn">取消</a>
  </div>
</form>
</#escape>
</@c.html>
