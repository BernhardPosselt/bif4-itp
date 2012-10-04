(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  __t('messages').InviteGroupMessage = (function(_super) {

    __extends(InviteGroupMessage, _super);

    function InviteGroupMessage(groupId, channelId, value) {
      this.groupId = groupId;
      this.channelId = channelId;
      this.value = value;
      InviteGroupMessage.__super__.constructor.call(this, 'invitegroup');
    }

    InviteGroupMessage.prototype.serialize = function() {
      var data;
      data = {
        channel_id: this.channelId,
        group_id: this.groupId,
        value: this.value
      };
      return InviteGroupMessage.__super__.serialize.call(this, data);
    };

    return InviteGroupMessage;

  })(Message);

}).call(this);
