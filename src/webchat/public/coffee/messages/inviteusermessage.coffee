angular.module('WebChat').factory '_InviteUserMessage', ['_Message', (_Message)->

    class InviteUserMessage extends _Message

        constructor: (@userId, @channelId, @value) ->
            super('inviteuser')


        serialize: ->
            data = 
                id: @channelId
                users: @userId
                value: @value
            return super(data)


    return InviteUserMessage

]