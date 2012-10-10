angular.module('WebChat').factory '_UserModel', ['_Model', (_Model) ->

    class UserModel extends _Model

        constructor: () ->
            super('user')


        create: (user) ->
            super( @enhance(user) )


        update: (user) ->
            super( @enhance(user) )


        enhance: (user) ->
            user.getFullName = ->
                @firstname + " " + @lastname
            return user

    return UserModel
]
