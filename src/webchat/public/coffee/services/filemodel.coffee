angular.module('WebChat').factory '_FileModel', ['_Model', (_Model) ->

    class FileModel extends _Model

        constructor: () ->
            super('file')
            # @create { id: 1, name: 'channel', groups: [0], users: [0, 1]}


    return FileModel
]
