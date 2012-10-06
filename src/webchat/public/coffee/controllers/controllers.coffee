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
    ['$scope', '_DialogueController', 
    ($scope, _DialogueController) -> 
        return new _DialogueController($scope)
    ]