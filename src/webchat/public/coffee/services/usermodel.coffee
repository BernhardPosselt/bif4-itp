angular.module('WebChat').factory '_UserModel', ['_Model', (_Model) ->

    class UserModel extends _Model

        constructor: () ->
            super('user')
            @create { id: 1, status: 'online', firstname: 'john', lastname: 'bingo', groups: [0, 1]}
            @create { id: 2, status: 'offline',  firstname: 'channel', lastname: 'ron', groups: [1]}

        create: (user) ->
            user.getFullName = ->
                @firstname + " " + @lastname
            super(user)

    return UserModel
]
