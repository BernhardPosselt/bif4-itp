angular.module('WebChat').factory '_InviteUserMessage', ['_Message', (_Message)->

    class InviteUserMessage extends _Message

        constructor: (@userId, @channelId, @value) ->
            super('inviteuser')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @userId
                value: @value
            return super(data)


    return InviteUserMessage

]