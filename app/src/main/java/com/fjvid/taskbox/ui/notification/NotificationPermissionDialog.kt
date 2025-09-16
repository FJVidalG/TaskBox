package com.fjvid.taskbox.ui.notification

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjvid.taskbox.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Dialog para gestion de permisos
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotificationPermissionDialog (
    onDismiss: () -> Unit
) {
    val notificationPermissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val context = LocalContext.current
    val viewModel: NotificationViewModel = hiltViewModel()

    var hasRequestedPermission by rememberSaveable { mutableStateOf(false) }
    var permissionsRequestCompleted by rememberSaveable { mutableStateOf(false) }

    val status = notificationPermissionState.status

    var wasRequest by viewModel.wasRequest

    LaunchedEffect(Unit) {
        viewModel.getNotificationPermission()
    }

    LaunchedEffect(status) {
        if (status is PermissionStatus.Granted && hasRequestedPermission && !permissionsRequestCompleted) {
            Toast.makeText(
                context,
                context.getString(R.string.notification_permission_granted),
                Toast.LENGTH_SHORT
            ).show() // lanzamos un toast en caso de que acepte los permisos
            permissionsRequestCompleted = true
            viewModel.saveNotificationPermission(true)
        }
        if (hasRequestedPermission) {
            permissionsRequestCompleted = true
        }
    }

    if (status is PermissionStatus.Denied) {
        BasicAlertDialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.title_notification_permission),
                        style = TextStyle(fontSize = 24.sp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (permissionsRequestCompleted || wasRequest) {
                        if (status.shouldShowRationale) {
                            Text(stringResource(R.string.notification_permission_rationale))
                            Button(onClick = {
                                notificationPermissionState.launchPermissionRequest()
                                hasRequestedPermission = true
                            }, modifier = Modifier.padding(top = 16.dp)) {
                                Text(
                                    stringResource(R.string.request_permission)
                                )
                            }
                        } else {
                            Text(stringResource(R.string.permission_denied_settings))
                            Button(onClick = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                                viewModel.saveNotificationPermission(true)
                            }) {
                                Text(stringResource(R.string.open_settings))
                            }
                            Button(
                                onClick = {
                                    onDismiss()
                                    viewModel.saveNotificationPermission(true)
                                },
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(stringResource(R.string.go_back))
                            }
                        }
                    } else {
                        Button(onClick = {
                            notificationPermissionState.launchPermissionRequest()
                            hasRequestedPermission = true
                        }, modifier = Modifier.padding(top = 16.dp)) {
                            Text(
                                stringResource(R.string.request_permission)
                            )
                        }
                    }
                }
            }
        }
    }
}




