(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  __t('messages').PingMessage = (function(_super) {

    __extends(PingMessage, _super);

    function PingMessage() {
      PingMessage.__super__.constructor.call(this, 'ping');
    }

    PingMessage.prototype.serialize = function() {
      var data;
      data = {};
      return PingMessage.__super__.serialize.call(this, data);
    };

    return PingMessage;

  })(Message);

}).call(this);
