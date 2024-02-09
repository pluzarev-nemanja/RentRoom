package com.example.rentproject.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MagnifierStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rentproject.R
import com.example.rentproject.data.data_source.relations.FloorWithRooms
import com.example.rentproject.domain.model.Room
import com.example.rentproject.presentation.util.RoomTabs
import com.example.rentproject.presentation.util.Screen
import com.example.rentproject.presentation.util.SettingsItems
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Currency
import kotlin.math.PI
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    groundFloor: List<FloorWithRooms>,
    firstFloor: List<FloorWithRooms>,
    secondFloor: List<FloorWithRooms>,
    navController: NavController,
    saveNavigatedRoom: (Room?) -> Unit,
    currency: Boolean
) {

    val curr by remember {
        mutableStateOf(currency)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { RoomTabs.entries.size })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }
    val items = listOf(
        SettingsItems(
            title = "All",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.HomeScreen.route
        ),
        SettingsItems(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = Screen.SettingsScreen.route
        ),
    )
    val drawerState = rememberAnimatedDrawerState(
        drawerWidth = 230.dp,
        drawerMode = DrawerMode.SlideBehind,
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    var expanded by remember { mutableStateOf(false) }
    val fraction by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 800), label = "",
    )

    AnimatedDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.route)
                            selectedItemIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        },
        state = drawerState,
        background = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.ic_launcher_background)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "RentRoom", style = MaterialTheme.typography.headlineSmall)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = "Open menu"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(pivotFractionX = 0f, pivotFractionY = .5f)
                        scaleX = (1f - .2f * fraction)
                        scaleY = (1f - .2f * fraction)
                    }
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex.value,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RoomTabs.entries.forEachIndexed { index, currentTab ->
                        Tab(
                            selected = selectedTabIndex.value == index, onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(currentTab.ordinal)
                                }
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.outline,
                            text = {
                                Text(text = currentTab.text)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selectedTabIndex.value == index)
                                        currentTab.selectedIcon else currentTab.unselectedIcon,
                                    contentDescription = "Tab Icon"
                                )
                            }
                        )
                    }

                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    val floor by remember {
                        when (page) {
                            0 -> mutableStateOf(groundFloor)
                            1 -> mutableStateOf(firstFloor)
                            else -> mutableStateOf(secondFloor)
                        }
                    }
                    val floorImage by remember {
                        when (page) {
                            0 -> mutableIntStateOf(R.drawable.dole)
                            1 -> mutableIntStateOf(R.drawable.prvi)
                            else -> mutableIntStateOf(R.drawable.potkrovlje)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        FloorPage(
                            floorName = RoomTabs.entries[selectedTabIndex.value].text,
                            roomsList = if (floor.isNotEmpty()) floor.first().rooms else emptyList(),
                            navController = navController,
                            saveNavigatedRoom = saveNavigatedRoom,
                            floorImage = floorImage,
                            currency = curr
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FloorPage(
    floorName: String,
    roomsList: List<Room>,
    navController: NavController,
    saveNavigatedRoom: (Room?) -> Unit,
    floorImage: Int,
    currency : Boolean

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var magnifierCenter by remember {
                mutableStateOf(Offset.Unspecified)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            // Show the magnifier in the initial position
                            onDragStart = { magnifierCenter = it },
                            // Magnifier follows the pointer during a drag event
                            onDrag = { _, delta ->
                                magnifierCenter = magnifierCenter.plus(delta)
                            },
                            // Hide the magnifier when a user ends the drag movement.
                            onDragEnd = { magnifierCenter = Offset.Unspecified },
                            onDragCancel = { magnifierCenter = Offset.Unspecified },
                        )
                    }
                    .magnifier(
                        sourceCenter = {
                            if (magnifierCenter != Offset.Unspecified) magnifierCenter - Offset(
                                0f,
                                200f
                            ) else magnifierCenter
                        },
                        zoom = 3f,
                        style = MagnifierStyle(
                            size = DpSize(height = 120.dp, width = 150.dp),
                            cornerRadius = 20.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = floorImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$floorName plan")
            Spacer(modifier = Modifier.height(8.dp))

        }
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "All beds : ")
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(roomsList) { room ->
                    RoomCard(
                        title = room.roomName,
                        available = room.available,
                        rent = room.rent,
                        navController = navController,
                        room = room,
                        saveNavigatedRoom = saveNavigatedRoom,
                        currency = currency
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: Dp = 12.dp,
    available: Boolean,
    rent: Float,
    navController: NavController,
    saveNavigatedRoom: (Room?) -> Unit,
    room: Room,
    currency: Boolean
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    val isAvailable by remember {
        mutableStateOf(if (available) "Yes" else "No")
    }
    val color by remember {
        if (available) mutableStateOf(Color(0xFF74E291))
        else mutableStateOf(Color(0xFFFF004D))
    }
    val curr by remember {
        if(!currency) mutableStateOf("Euro") else mutableStateOf("Rsd")
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.4f),
        ),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.5f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Available : $isAvailable",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Reserved : ${room.reservationPeriod} days",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daily rent : $rent $curr",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(onClick = {
                            saveNavigatedRoom.invoke(room)
                            navController.navigate(Screen.RoomDetailsScreen.route)
                        }) {
                            Text(text = "See details")
                        }
                    }
                }
            }
        }
    }
}

@Stable
interface AnimatedDrawerState {
    var density: Float
    val drawerWidth: Dp

    val drawerTranslationX: Float
    val drawerElevation: Float

    val backgroundTranslationX: Float
    val backgroundAlpha: Float

    val contentScaleX: Float
    val contentScaleY: Float
    val contentTranslationX: Float
    val contentTransformOrigin: TransformOrigin

    suspend fun open()
    suspend fun close()
}

private const val AnimationDurationMillis = 600
private const val DrawerMaxElevation = 8f

sealed interface DrawerMode {
    data class SlideRight(
        val drawerGap: Dp
    ) : DrawerMode

    object SlideBehind : DrawerMode
}

private val DrawerMode.scaleFactor: Float
    get() = when (this) {
        is DrawerMode.SlideRight -> .2f
        DrawerMode.SlideBehind -> .4f
    }

private fun DrawerMode.translationX(
    drawerWidth: Float,
    fraction: Float,
    density: Float,
) = when (this) {
    is DrawerMode.SlideRight -> (drawerWidth + drawerGap.value * density) * fraction
    DrawerMode.SlideBehind -> ((.6f * drawerWidth) * sin(fraction * PI)).toFloat()
}

private val DrawerMode.transformOrigin: TransformOrigin
    get() = when (this) {
        is DrawerMode.SlideRight -> TransformOrigin(pivotFractionX = 0f, pivotFractionY = .5f)
        DrawerMode.SlideBehind -> TransformOrigin(pivotFractionX = 1f, pivotFractionY = .5f)
    }

@Stable
class AnimatedDrawerStateImpl(
    override val drawerWidth: Dp,
    private val drawerMode: DrawerMode,
) : AnimatedDrawerState {

    private val animation = Animatable(0f)
    private val animationY = Animatable(0f)

    override var density by mutableStateOf(1f)

    override val drawerTranslationX: Float
        get() = -drawerWidth.value * density * (1f - animation.value)

    override val drawerElevation: Float
        get() = DrawerMaxElevation * animation.value

    override val backgroundTranslationX: Float
        get() = animation.value * drawerWidth.value * density

    override val backgroundAlpha: Float
        get() = .25f * animation.value

    override val contentScaleX: Float
        get() = 1f - drawerMode.scaleFactor * animation.value

    override val contentScaleY: Float
        get() = 1f - drawerMode.scaleFactor * animationY.value

    override val contentTranslationX: Float
        get() = drawerMode.translationX(
            drawerWidth = drawerWidth.value * density,
            fraction = animation.value,
            density = density,
        )

    override val contentTransformOrigin: TransformOrigin
        get() = drawerMode.transformOrigin

    override suspend fun open() {
        coroutineScope {
            launch {
                animation.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = AnimationDurationMillis)
                )
            }
            launch {
                animationY.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = AnimationDurationMillis,
                        delayMillis = AnimationDurationMillis / 4,
                    ),
                )
            }
        }
    }

    override suspend fun close() {
        coroutineScope {
            launch {
                animation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = AnimationDurationMillis)
                )
            }
            launch {
                animationY.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = AnimationDurationMillis,
                        delayMillis = AnimationDurationMillis / 4,
                    ),
                )
            }
        }
    }
}

@Composable
fun rememberAnimatedDrawerState(
    drawerWidth: Dp,
    drawerMode: DrawerMode,
): AnimatedDrawerState = remember {
    AnimatedDrawerStateImpl(
        drawerWidth = drawerWidth,
        drawerMode = drawerMode,
    )
}

@Composable
fun AnimatedDrawer(
    modifier: Modifier = Modifier,
    state: AnimatedDrawerState = rememberAnimatedDrawerState(
        drawerWidth = 250.dp,
        DrawerMode.SlideRight(drawerGap = 16.dp),
    ),
    drawerContent: @Composable () -> Unit,
    background: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            drawerContent()
            background()
            content()
        }
    ) { measurables, constraints ->
        state.density = density
        val drawerWidthPx = state.drawerWidth.value * density
        val (drawerContentMeasurable, backgroundMeasurable, contentMeasurable) = measurables
        val drawerContentConstraints = Constraints.fixed(
            width = drawerWidthPx.coerceAtMost(constraints.maxWidth.toFloat()).toInt(),
            height = constraints.maxHeight,
        )
        val drawerContentPlaceable = drawerContentMeasurable.measure(drawerContentConstraints)
        val contentConstraints = Constraints.fixed(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        )
        val contentPlaceable = contentMeasurable.measure(contentConstraints)
        val backgroundPlaceable = backgroundMeasurable.measure(
            Constraints.fixed(
                width = constraints.maxWidth,
                height = constraints.maxHeight,
            )
        )
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            backgroundPlaceable.placeRelativeWithLayer(
                IntOffset.Zero
            ) {
                translationX = state.backgroundTranslationX
                alpha = state.backgroundAlpha
            }
            contentPlaceable.placeRelativeWithLayer(
                IntOffset.Zero,
            ) {
                transformOrigin = state.contentTransformOrigin
                scaleX = state.contentScaleX
                scaleY = state.contentScaleY
                translationX = state.contentTranslationX
            }
            drawerContentPlaceable.placeRelativeWithLayer(
                IntOffset.Zero,
            ) {
                translationX = state.drawerTranslationX
                shadowElevation = state.drawerElevation
            }
        }

    }
}