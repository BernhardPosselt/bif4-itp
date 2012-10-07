angular.module('WebChat').factory '_UserModel', ['_Model', (_Model) ->

    class UserModel extends _Model

        constructor: () ->
            super('user')

        create: (user) ->
            user.getFullName = ->
                @firstname + " " + @lastname
            super(user)

    return UserModel
]
