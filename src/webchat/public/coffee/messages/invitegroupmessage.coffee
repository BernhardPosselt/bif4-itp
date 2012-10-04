angular.module('WebChat').factory '_InviteGroupMessage', ['_Message', (_Message)->

    class InviteGroupMessage extends _Message

        constructor: (@groupId, @channelId, @value) ->
            super('invitegroup')


        serialize: ->
            data = 
                channel_id: @channelId
                group_id: @groupId
                value: @value
            return super(data)


    return InviteGroupMessage
]