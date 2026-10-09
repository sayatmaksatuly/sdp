param([Parameter(ValueFromRemainingArguments = $true)][string[]]$GradleArguments = @('build'))

$ErrorActionPreference = 'Stop'
$projectRoot = $PSScriptRoot
$candidates = [System.Collections.Generic.List[string]]::new()
if ($env:JAVA_HOME) { $candidates.Add($env:JAVA_HOME) }
$jetbrainsRoot = Join-Path $env:ProgramFiles 'JetBrains'
if (Test-Path -LiteralPath $jetbrainsRoot) {
    Get-ChildItem -LiteralPath $jetbrainsRoot -Directory | ForEach-Object {
        $candidates.Add((Join-Path $_.FullName 'jbr'))
    }
}
$jdkRoot = Join-Path $env:USERPROFILE '.jdks'
if (Test-Path -LiteralPath $jdkRoot) {
    Get-ChildItem -LiteralPath $jdkRoot -Directory | ForEach-Object { $candidates.Add($_.FullName) }
}
$javaRoot = Join-Path $env:ProgramFiles 'Java'
if (Test-Path -LiteralPath $javaRoot) {
    Get-ChildItem -LiteralPath $javaRoot -Directory | ForEach-Object { $candidates.Add($_.FullName) }
}
$adoptiumRoot = Join-Path $env:ProgramFiles 'Eclipse Adoptium'
if (Test-Path -LiteralPath $adoptiumRoot) {
    Get-ChildItem -LiteralPath $adoptiumRoot -Directory | ForEach-Object { $candidates.Add($_.FullName) }
}
$selectedJdk = $null
foreach ($candidate in $candidates) {
    $releaseFile = Join-Path $candidate 'release'
    if ((Test-Path -LiteralPath (Join-Path $candidate 'bin/javac.exe')) -and
            (Test-Path -LiteralPath $releaseFile) -and
            ((Get-Content -LiteralPath $releaseFile -Raw) -match 'JAVA_VERSION="21(?:\.|"|-)')) {
        $selectedJdk = $candidate
        break
    }
}
if (-not $selectedJdk) {
    throw 'Set JAVA_HOME to a JDK 21 installation containing javac.exe.'
}
$env:JAVA_HOME = $selectedJdk
$env:GRADLE_USER_HOME = Join-Path $projectRoot '.tools/gradle-home'
# Work around the local JBR Windows loopback pipe failure without changing system settings.
$pipeDirectory = Join-Path $env:SystemRoot 'Temp'
$env:JAVA_TOOL_OPTIONS = ($env:JAVA_TOOL_OPTIONS +
    ' -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.WindowsSelectorProvider' +
    " -Djdk.net.unixdomain.tmpdir=$pipeDirectory").Trim()
Push-Location $projectRoot
try {
    & (Join-Path $projectRoot 'gradlew.bat') @GradleArguments
    $gradleExit = $LASTEXITCODE
} finally {
    Pop-Location
}
exit $gradleExit
