(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  __t('messages').ReadonlyGroupMessage = (function(_super) {

    __extends(ReadonlyGroupMessage, _super);

    function ReadonlyGroupMessage(groupId, channelId, value) {
      this.groupId = groupId;
      this.channelId = channelId;
      this.value = value;
      ReadonlyGroupMessage.__super__.constructor.call(this, 'readonlygroup');
    }

    ReadonlyGroupMessage.prototype.serialize = function() {
      var data;
      data = {
        channel_id: this.channelId,
        user_id: this.groupId,
        value: this.value
      };
      return ReadonlyGroupMessage.__super__.serialize.call(this, data);
    };

    return ReadonlyGroupMessage;

  })(Message);

}).call(this);
