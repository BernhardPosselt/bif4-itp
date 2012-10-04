(function() {

  angular.module('WebChat').factory('activechannel', [
    '$rootScope', function($rootScope) {
      var ActiveChannel;
      ActiveChannel = {};
      ActiveChannel.activeChannelId = null;
      ActiveChannel.setActiveChannelId = function(id) {
        this.activeChannelId = id;
        return $rootScope.$broadcast('changed_channel');
      };
      ActiveChannel.getActiveChannelId = function() {
        return this.activeChannelId;
      };
      return ActiveChannel;
    }
  ]);

}).call(this);
