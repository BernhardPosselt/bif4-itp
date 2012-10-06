angular.module('WebChat').factory '_UserModel', ['_Model', (_Model) ->

    class UserModel extends _Model

        constructor: () ->
            super('user')
            # @create { id: 1, name: 'channel', groups: [0], users: [0, 1]}


    return UserModel
]
