--
--  ${patchname}
--
--
--  ${versioncontrol}
--

Clear Screen


define LOGFILE   = 'PATCH${patchnumber}.log';
define PATCHNO   = ${patchnumber};


set define off;

Prompt ======================================================================================
Prompt |                     Installing ${patchname}       |
Prompt | Please ensure Patch notes document has been read, and all objects affected by this |
Prompt |                              deployment are backed up.                             |
Prompt | !!!!!!!!!!!!!!!!!!!!!!!Press ENTER to confirm and continue!!!!!!!!!!!!!!!!!!!!!!!! |
Prompt ======================================================================================
Pause

Select  TO_CHAR(Sysdate, 'DD-Mon-YYYY HH24:Mi:SS') As RUN_DATE
From    DUAL;

set feedback off;
set verify off;
set termout off;

Insert Into SVE_EFIX (SVE_EFIX_NUMBER, SVE_SCRIPT_NAME, SVE_DETAILS)
Values               ( ${patchnumber},
'InstallScript.sql',
'${pacthdescription}'
);
Commit;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Procedures
Prompt Press ENTER to continue
Prompt ===============================================================================

<#list procedures as procedure>
@procedure/${procedure}.sql;
</#list>

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Functions
Prompt Press ENTER to continue
Prompt ===============================================================================

<#list functions as function>
@function/${function}.sql;
</#list>

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Types
Prompt Press ENTER to continue
Prompt ===============================================================================

<#list types as type>
@type/${type}.sql;
</#list>
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Apex
Prompt Press ENTER to continue
Prompt ===============================================================================

<#list apexpages as apex>
@apex/${apex}.sql;
</#list>
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Wrapping Code
Prompt Press ENTER to continue
Prompt ===============================================================================

Begin
VX_WRAPPER.Wrap(i_name => VARCHAR_TABLE(<#list procedures as procedure> '${procedure}', </#list><#list functions as function>'${function}',</#list>
)
);

DBMS_OUTPUT.PUT_LINE('Code Wrapped successfully');
Exception
When Others
Then
DBMS_OUTPUT.PUT_LINE('Error Wrapping Code');
End;
/

----------------------------------------------------------------------------------
set termout on;
Prompt
Prompt ===============================================================================
Prompt Patch Complete, see log file PATCH${patchnumber}.log for details
Prompt Press ENTER To Exit

Spool Off;