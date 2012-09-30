angular.module('WebChat').filter 'userInGroup', ->
    return (items, args) ->
        result = []
        for item in items
            for group_id in item.groups
                if args.groupid == group_id
                    result.push(item)
        return result