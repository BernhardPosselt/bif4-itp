(function() {
  var Message,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  Message = require('webchat.Message');

  __t('messages').ReadonlyUserMessage = (function(_super) {

    __extends(ReadonlyUserMessage, _super);

    function ReadonlyUserMessage(userId, channelId, value) {
      this.userId = userId;
      this.channelId = channelId;
      this.value = value;
      ReadonlyUserMessage.__super__.constructor.call(this, 'readonlyuser');
    }

    ReadonlyUserMessage.prototype.serialize = function() {
      var data;
      data = {
        channel_id: this.channelId,
        user_id: this.userId,
        value: this.value
      };
      return ReadonlyUserMessage.__super__.serialize.call(this, data);
    };

    return ReadonlyUserMessage;

  })(Message);

  webchat.ReadonlyUserMessage = ReadonlyUserMessage;

}).call(this);
