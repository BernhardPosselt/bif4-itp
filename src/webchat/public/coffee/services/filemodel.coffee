angular.module('WebChat').factory '_FileModel', ['_Model', (_Model) ->

    class FileModel extends _Model

        constructor: () ->
            super('file')


        create: (file) ->
            file.getSize = =>
                return @kbToHumanReadable(file.size, 1)
            super(file)


        kbToHumanReadable: (bytes, precision) ->
            kilobyte = 1024
            megabyte = kilobyte * 1024;
            gigabyte = megabyte * 1024;
            terabyte = gigabyte * 1024;

            if bytes >= kilobyte and bytes < megabyte
                return (bytes / kilobyte).toFixed(precision) + ' KB'
            if bytes >= megabyte and bytes < gigabyte
                return (bytes / megabyte).toFixed(precision) + ' MB'
            else if bytes >= gigabyte and bytes < terabyte
                return (bytes / gigabyte).toFixed(precision) + ' GB'
            else if bytes >= terabyte
                return (bytes / terabyte).toFixed(precision) + ' TB'
            else
                return bytes + ' B'

    return FileModel
]
