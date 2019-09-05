/**
 * 初始化详情对话框
 */
var TbMemberInfoDlg = {
    tbMemberInfoData : {}
};

/**
 * 清除数据
 */
TbMemberInfoDlg.clearData = function() {
    this.tbMemberInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TbMemberInfoDlg.set = function(key, val) {
    this.tbMemberInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TbMemberInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TbMemberInfoDlg.close = function() {
    parent.layer.close(window.parent.TbMember.layerIndex);
}

/**
 * 收集数据
 */
TbMemberInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('code')
    .set('address')
    .set('phone')
    .set('email')
    .set('version')
    .set('userId');
}

/**
 * 提交添加
 */
TbMemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tbMember/add", function(data){
        Feng.success("添加成功!");
        window.parent.TbMember.table.refresh();
        TbMemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tbMemberInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TbMemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tbMember/update", function(data){
        Feng.success("修改成功!");
        window.parent.TbMember.table.refresh();
        TbMemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tbMemberInfoData);
    ajax.start();
}

TbMemberInfoDlg.getUserList = function() {
    /*var index = layer.open({
        type: 2,
        title: '会员选择',
        area: ['300px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tbMember/user/list' + this.seItem.id
    });
    this.layerIndex = index;*/
    var userId = $("#userId");
    var userIdOffset = $("#userId").offset();
    $("#menuUserIdContent").css({
        left: userIdOffset.left + "px",
        top: userIdOffset.top + userId.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);


    }

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
        event.target).parents("#menuContent").length > 0)) {
        TbMemberInfoDlg.hideDeptSelectTree();
    }
}

TbMemberInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

$(function() {

});
