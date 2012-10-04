(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  __t('messages').ModUserMessage = (function(_super) {

    __extends(ModUserMessage, _super);

    function ModUserMessage(userId, channelId, value) {
      this.userId = userId;
      this.channelId = channelId;
      this.value = value;
      ModUserMessage.__super__.constructor.call(this, 'moduser');
    }

    ModUserMessage.prototype.serialize = function() {
      var data;
      data = {
        channel_id: this.channelId,
        user_id: this.userId,
        value: this.value
      };
      return ModUserMessage.__super__.serialize.call(this, data);
    };

    return ModUserMessage;

  })(Message);

}).call(this);
