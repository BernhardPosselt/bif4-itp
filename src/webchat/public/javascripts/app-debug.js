var __t;

__t = function(ns) {
  var curr, index, part, parts, _i, _len;
  curr = null;
  parts = [].concat = ns.split(".");
  for (index = _i = 0, _len = parts.length; _i < _len; index = ++_i) {
    part = parts[index];
    if (curr === null) {
      curr = eval(part);
      continue;
    } else {
      if (curr[part] == null) {
        curr = curr[part] = {};
      } else {
        curr = curr[part];
      }
    }
  }
  return curr;
};

var services = {};
var messages = {};
var controllers = {};
var filters = {};

document.write('<scri'+'pt src="/toaster/app.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/services/activechannel.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/services/websocket.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/services/channelmodel.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/services/model.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/inviteusermessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/pingmessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/modusermessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/readonlyusermessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/message.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/invitegroupmessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/joinmessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/readonlygroupmessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/modgroupmessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/messages/sendmessage.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/ui.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/controllers/channellistcontroller.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/controllers/channel_controller.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/filters/user_in_channel_filter.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/filters/group_in_channel_filter.js"></scr'+'ipt>')
document.write('<scri'+'pt src="/toaster/filters/user_in_group_filter.js"></scr'+'ipt>')