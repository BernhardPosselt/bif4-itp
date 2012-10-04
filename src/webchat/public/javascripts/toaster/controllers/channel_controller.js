(function() {

  __t('controllers').ChannelController = (function() {

    function ChannelController($scope, websocket, activechannel, channelmodel) {
      var _this = this;
      this.websocket = websocket;
      this.activechannel = activechannel;
      this.channelmodel = channelmodel;
      this.activeChannelId = null;
      this.getActiveChannelId = function() {
        return _this.activechannel.getActiveChannelId();
      };
      this.setActiveChannelId = function(id) {
        return _this.activechannel.setActiveChannelId(id);
      };
      this.simpleChannelMessage = function(id, Message, value) {
        var activeChannelId, message;
        activeChannelId = _this.getActiveChannelId();
        if (activeChannelId !== null) {
          message = new Message(id, activeChannelId, value);
          return _this.websocket.sendJSON(message.serialize());
        }
      };
      $scope.getActiveChannelId = function() {
        return _this.getActiveChannelId();
      };
      $scope.join = function(id) {
        var message;
        message = new WebChat.JoinMessage(id);
        _this.websocket.sendJSON(message.serialize());
        _this.setActiveChannelId(id);
        return $scope.selected = id;
      };
      $scope.sendMessage = function(textInput, messageType) {
        var activeChannelId, message;
        activeChannelId = _this.getActiveChannelId();
        if (activeChannelId !== null) {
          message = new WebChat.SendMessage(textInput, messageType, activeChannelId);
          return _this.websocket.sendJSON(message.serialize());
        }
      };
      $scope.inviteUser = function(userId, value) {
        return _this.simpleChannelMessage(userId, WebChat.InviteUserMessage, value);
      };
      $scope.inviteGroup = function(groupId, value) {
        return _this.simpleChannelMessage(groupId, WebChat.InviteGroupMessage, value);
      };
      $scope.modUser = function(userId, value) {
        return _this.simpleChannelMessage(userId, WebChat.ModUserMessage, value);
      };
      $scope.modGroup = function(groupId, value) {
        return _this.simpleChannelMessage(groupId, WebChat.ModGroupMessage, value);
      };
      $scope.readonlyUser = function(userId, value) {
        return _this.simpleChannelMessage(userId, WebChat.ReadonlyUserMessage, value);
      };
      $scope.readonlyGroup = function(groupId, value) {
        return _this.simpleChannelMessage(groupId, WebChat.ReadonlyGroupMessage, value);
      };
    }

    return ChannelController;

  })();

  angular.module('WebChat').controller('ChannelController', [
    '$scope', 'websocket', 'activechannel', 'channelmodel', function($scope, websocket, activechannel, channelmodel) {
      return new ChannelController($scope, websocket, activechannel, channelmodel);
    }
  ]);

}).call(this);
