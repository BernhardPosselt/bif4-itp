angular.module('WebChat').factory '_EditProfileMessage',
['_Message', (_Message) ->

    class EditProfileMessage extends _Message

        constructor: (@id, @username, @prename, @lastname, @password, @email) ->
            super('profileupdate')


        serialize: ->
            data =
                id: @id
                username: @username
                prename: @prename
                lastname: @lastname
                password: @password
                email: @email
            return super(data)


    return EditProfileMessage

]