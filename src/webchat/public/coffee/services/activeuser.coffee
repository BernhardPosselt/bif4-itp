angular.module('WebChat').factory 'ActiveUser', ['$rootScope', ($rootScope) ->
    ActiveUser = 
        id: null

    $rootScope.$on 'message', (scope, message) ->
        if message.type == 'activeuser'
            ActiveUser.id = message.data.id

    return ActiveUser
]