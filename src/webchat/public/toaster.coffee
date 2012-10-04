syntaxhighlighterPath = 'syntaxhighlighter/scripts/'
syntaxhighlighter = ['shBrushAppleScript.js', 'shBrushAS3.js', 'shBrushBash.js', 
                     'shBrushColdFusion.js', 'shBrushCpp.js', 'shBrushCSharp.js', 
                     'shBrushCss.js', 'shBrushDelphi.js', 'shBrushDiff.js', 'shBrushErlang.js', 
                     'shBrushGroovy.js', 'shBrushJavaFX.js', 'shBrushJava.js', 
                     'shBrushJScript.js', 'shBrushPerl.js', 'shBrushPhp.js', 
                     'shBrushPlain.js', 'shBrushPowerShell.js', 'shBrushPython.js', 
                     'shBrushRuby.js', 'shBrushSass.js', 'shBrushScala.js', 
                     'shBrushSql.js', 'shBrushVb.js', 'shBrushXml.js', 'shCore.js']
others = ['angular-1.0.2/angular.js', 'jquery.js', 'jquery.tablesorter.min.js']

vendors = []
vendorPath = 'public/vendors/'

for item in syntaxhighlighter
    fullPath = vendorPath + syntaxhighlighterPath + item
    vendors.push(fullPath)

for item in others
    fullPath = vendorPath + item
    vendors.push(fullPath)

toast 'public/coffee'
    httpdfolder: 'public/javascripts'
    release: 'public/javascripts/app.js'
    debug: 'public/javascripts/app-debug.js'
    vendors: vendors