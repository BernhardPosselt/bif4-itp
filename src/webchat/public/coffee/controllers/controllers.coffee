angular.module('WebChat').controller 'ChannelListController', 
    ['$scope', '_ChannelListController', 'WebChatWebSocket', 'ActiveChannel', 
     'ChannelModel', ($scope, _ChannelListController, WebChatWebSocket, ActiveChannel, ChannelModel) -> 
        return new _ChannelListController($scope, WebChatWebSocket, ActiveChannel, ChannelModel)
    ]

angular.module('WebChat').controller 'DialogueController', 
    ['$scope', '_DialogueController', 'ActiveChannel', 'ChannelModel', 
    ($scope, _DialogueController, ActiveChannel, ChannelModel) -> 
        return new _DialogueController($scope, ActiveChannel, ChannelModel)
    ]