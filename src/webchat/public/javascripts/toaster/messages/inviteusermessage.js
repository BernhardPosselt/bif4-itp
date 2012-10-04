(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  __t('messages').InviteUserMessage = (function(_super) {

    __extends(InviteUserMessage, _super);

    function InviteUserMessage(userId, channelId, value) {
      this.userId = userId;
      this.channelId = channelId;
      this.value = value;
      InviteUserMessage.__super__.constructor.call(this, 'inviteuser');
    }

    InviteUserMessage.prototype.serialize = function() {
      var data;
      data = {
        channel_id: this.channelId,
        user_id: this.userId,
        value: this.value
      };
      return InviteUserMessage.__super__.serialize.call(this, data);
    };

    return InviteUserMessage;

  })(Message);

}).call(this);
