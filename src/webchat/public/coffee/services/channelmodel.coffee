angular.module('WebChat').factory '_ChannelModel', ['_Model', (_Model) ->

    class ChannelModel extends _Model

        constructor: () ->
            super('channel')

            @create { id: 1, name: 'channel', groups: [0], users: [0, 1], files: [0, 1]}


    return ChannelModel
]
