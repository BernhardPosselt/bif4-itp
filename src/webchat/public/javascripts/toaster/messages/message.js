(function() {
  var webchat;

  webchat = require('webchat');

  webchat.Message = (function() {

    function _Class(type) {
      this.type = type;
    }

    _Class.prototype.serialize = function(data) {
      var message;
      message = {
        type: this.type,
        data: data
      };
      return message;
    };

    return _Class;

  })();

}).call(this);
