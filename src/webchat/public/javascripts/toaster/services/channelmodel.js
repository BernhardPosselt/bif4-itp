(function() {
  var Model,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  Model = require('webchat.Model');

  __t('services').ChannelModel = (function(_super) {

    __extends(ChannelModel, _super);

    function ChannelModel() {
      ChannelModel.__super__.constructor.call(this, 'channel');
      this.create({
        id: 1,
        name: 'channel',
        groups: [0],
        users: [0, 1]
      });
    }

    return ChannelModel;

  })(Model);

  angular.module('WebChat').factory('channelmodel', function() {
    var channel;
    channel = new ChannelModel();
    return channel;
  });

}).call(this);
