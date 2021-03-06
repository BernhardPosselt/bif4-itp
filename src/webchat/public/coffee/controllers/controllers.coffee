angular.module('WebChat').controller 'ChannelListController', 
    ['$scope', '_ChannelListController', 
     ($scope, _ChannelListController) -> 
        return new _ChannelListController($scope)
    ]

angular.module('WebChat').controller 'GroupListController', 
    ['$scope', '_GroupListController',
     ($scope, _GroupListController) -> 
        return new _GroupListController($scope)
    ]

angular.module('WebChat').controller 'MessageController', 
    ['$scope', '_MessageController',
     ($scope, _MessageController) -> 
        return new _MessageController($scope)
    ]

angular.module('WebChat').controller 'GroupsInChannelController', 
    ['$scope', '_GroupsInChannelController',
     ($scope, _GroupsInChannelController) -> 
        return new _GroupsInChannelController($scope)
    ]

angular.module('WebChat').controller 'FilesInChannelController', 
    ['$scope', '_FilesInChannelController', 
     ($scope, _FilesInChannelController) -> 
        return new _FilesInChannelController($scope)
    ]

angular.module('WebChat').controller 'DialogueController', 
    ['$scope', '_DialogueController', 'ActiveChannel', 'ChannelModel',
     '_NewChannelMessage', '_ChangeTopicMessage', '_CloseChannelMessage',
     '_ChangeChannelNameMessage', '_EditProfileMessage', 'ActiveUser', 'UserModel',
    ($scope, _DialogueController, ActiveChannel, ChannelModel, _NewChannelMessage
        _ChangeTopicMessage, _CloseChannelMessage, _ChangeChannelNameMessage,
        _EditProfileMessage, ActiveUser, UserModel) ->
        return new _DialogueController($scope, ActiveChannel, ChannelModel, _NewChannelMessage
        _ChangeTopicMessage, _CloseChannelMessage, _ChangeChannelNameMessage,
        _EditProfileMessage, ActiveUser, UserModel)
    ]
