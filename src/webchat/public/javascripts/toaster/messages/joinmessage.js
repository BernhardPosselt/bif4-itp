(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  __t('messages').JoinMessage = (function(_super) {

    __extends(JoinMessage, _super);

    function JoinMessage(id) {
      this.id = id;
      JoinMessage.__super__.constructor.call(this, 'join');
    }

    JoinMessage.prototype.serialize = function() {
      var data;
      data = {
        id: this.id
      };
      return JoinMessage.__super__.serialize.call(this, data);
    };

    return JoinMessage;

  })(Message);

}).call(this);
