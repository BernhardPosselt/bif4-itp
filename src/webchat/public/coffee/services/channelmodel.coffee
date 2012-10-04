Model = require('webchat.Model')

class ChannelModel extends Model

    constructor: () ->
        super('channel')

        @create { id: 1, name: 'channel', groups: [0], users: [0, 1]}


angular.module('WebChat').factory 'channelmodel', () ->
    channel = new ChannelModel()
    return channel
