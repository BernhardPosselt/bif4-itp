angular.module('WebChat').controller 'ChannelController', 
    ['$scope', '_ChannelController', 'WebChatWebSocket', 'ActiveChannel', 
     'ChannelModel', ($scope, _ChannelController, WebChatWebSocket, ActiveChannel, ChannelModel) -> 
        return new _ChannelController($scope, WebChatWebSocket, ActiveChannel, ChannelModel)
    ]