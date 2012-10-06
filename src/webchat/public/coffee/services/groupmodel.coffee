angular.module('WebChat').factory '_GroupModel', ['_Model', (_Model) ->

    class GroupModel extends _Model

        constructor: () ->
            super('group')
            @create { id: 0, name: 'my group'}
            @create { id: 1, name: 'my group 2'}


    return GroupModel
]
